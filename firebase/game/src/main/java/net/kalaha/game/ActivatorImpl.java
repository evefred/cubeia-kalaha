package net.kalaha.game;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static net.kalaha.common.TableState.CLOSED;
import static net.kalaha.data.entities.GameStatus.FINISHED;
import static net.kalaha.data.entities.GameType.KALAHA;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.manager.UserManager;
import net.kalaha.data.util.JpaInitializer;
import net.kalaha.game.action.Close;
import net.kalaha.game.action.Transformer;
import net.kalaha.game.logic.KalahaBoard;
import net.kalaha.table.api.CreateGameRequest;
import net.kalaha.table.api.CreateGameResponse;
import net.kalaha.table.api.GetTableRequest;
import net.kalaha.table.api.GetTableResponse;
import net.kalaha.table.api.TableManager;
import net.kalaha.table.api.TableRequestAction;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.common.AttributeValue;
import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.GameActivator;
import com.cubeia.firebase.api.game.activator.TableFactory;
import com.cubeia.firebase.api.game.lobby.LobbyTable;
import com.cubeia.firebase.api.game.lobby.LobbyTableFilter;
import com.cubeia.firebase.api.routing.ActivatorAction;
import com.cubeia.firebase.api.routing.RoutableActivator;
import com.cubeia.firebase.api.server.SystemException;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;

public class ActivatorImpl implements GameActivator, /*RequestAwareActivator,*/ RoutableActivator {
	
	private static final long TASK_DELAY = 60000; // one minute

	private final Logger log = Logger.getLogger(getClass());

	private Injector injector;
	private ActivatorContext context;
	private TableManager tableManager; // service
	
	@Inject
	private GameManager gameManager;
	
	@Inject
	private UserManager userManager;
	
	@Inject
	private OperatorConfig operatorConfig;
	
	@Inject
	private SystemTime time;
	
	// table to game and reverse
	private ActivatorMapping mapping = new ActivatorMapping();
	
	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> taskFuture;

	@Override
	public void destroy() { 
		scheduler.shutdown();
	}

	@Override
	public void init(ActivatorContext context) throws SystemException {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		tableManager = context.getServices().getServiceInstance(TableManager.class);
		injector = Guice.createInjector(new ManagerModule(), new GameModule(), new JpaPersistModule("kalaha"));
		injector.injectMembers(this);
		this.context = context;
	}
	
	@Override
	public void onAction(ActivatorAction<?> action) {
		TableRequestAction q = (TableRequestAction) action.getData();
		if(q instanceof GetTableRequest) {
			handleGetTable((GetTableRequest)q);
		} else if(q instanceof CreateGameRequest) {
			handleCreateGame((CreateGameRequest)q);
		}
	}

	@Override
	public void start() { 
		injector.getInstance(JpaInitializer.class);
		taskFuture = scheduler.scheduleWithFixedDelay(new TableTask(), TASK_DELAY, TASK_DELAY, MILLISECONDS);
	}

	@Override
	public void stop() { 
		taskFuture.cancel(false);
	}
	

	// --- TEST METHODS --- //
	
	UserManager getUserManager() {
		return userManager;
	}
	
	GameManager getGameManager() {
		return gameManager;
	}
	

	// --- PRIVATE METHODS --- //
	
	private void handleCreateGame(CreateGameRequest q) {
		User user = userManager.getUser(q.getUserId());
		User opponent = userManager.getUser(q.getOpponentId());
		// TODO: Check if users exists...
		Game game = gameManager.createGame(KALAHA, user, opponent, operatorConfig.getDefaultMoveTimeout(user.getOperatorId()), KalahaBoard.getInitState(6));
		int tableId = getTableForGame(game.getId(), q.getUserId());
		if(tableId != -1) {
			dispatch(new CreateGameResponse(q, game.getId(), tableId));
		} else {
			log.error("Could not retreive table for game " + game.getId());
			// TODO: Handle failure
		}
	}
	
	private void handleGetTable(GetTableRequest q) {
		int tableId = getTableForGame(q.getGameId(), q.getUserId());
		if(tableId != -1) {
			dispatch(new GetTableResponse(q, tableId));
		} else {
			log.error("Could not retreive table for game " + q.getGameId());
			// TODO: Handle failure
		}
	}

	private int getTableForGame(long gameId, long userId) {
		int tableId = -1;
		synchronized(mapping) {
			tableId = mapping.getTableForGame(gameId);
			if(tableId == -1) {
				tableId = createTable(gameId, userId);
			} else {
				log.debug("Resusing table id " + tableId + " for game " + gameId);
			}
		}
		return tableId;
	}
	
	private void dispatch(TableRequestAction action) {
		tableManager.sendToClient(action);
	}

	private int createTable(long gameId, long userId) {
		log.debug("Ressurecting game " + gameId + " for player " + userId);
		final Game game = gameManager.getGame(gameId);
		if (game == null) {
			log.fatal("Received request for game " + gameId + " which doesn't exist!");
			return -1; // EARLY RETURN
		}
		if (game.getStatus() == FINISHED) {
			log.fatal("Received request for game " + gameId + " which is ended!");
			return -1; // EARLY RETURN
		}
		TableFactory fact = context.getTableFactory();
		LobbyTable table = fact.createTable(2, new ActivatorParticipant(game));
		log.debug("Table " + table.getTableId() + " created for game " + gameId + "; on behalf of player " + userId);
		mapping.put(gameId, table.getTableId());
		return table.getTableId();
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	private class TableTask implements Runnable {
		
		private Transformer transformer = new Transformer();
		
		@Override
		public void run() {
			log.debug("Table task running.");
			destroyClosedTables();
			closeOldEndEmpty();
		}

		private void destroyClosedTables() {
			log.debug("Searching for closed tables");
			LobbyTable[] tables = context.getTableFactory().listTables(new LobbyTableFilter() {
				
				@Override
				public boolean accept(Map<String, AttributeValue> atts) {
					AttributeValue state = atts.get("state");
					return CLOSED.name().equals(state.getStringValue());
				}
			});
			log.debug("Found " + tables.length + " closed tables");
			for (LobbyTable t : tables) {
				log.debug("Destroying table " + t.getTableId());
				context.getTableFactory().destroyTable(t.getTableId(), true);
			}
		}

		private void closeOldEndEmpty() {
			log.debug("Searching for old and empty tables");
			LobbyTable[] tables = context.getTableFactory().listTables(new ActivatorTableFilter(time, TASK_DELAY));
			log.debug("Found " + tables.length + " old and empty tables");
			for (LobbyTable t : tables) {
				log.debug("Sending close action to table " + t.getTableId());
				GameObjectAction action = new GameObjectAction(t.getTableId());
				action.setAttachment(transformer.toString(new Close()));
				context.getActivatorRouter().dispatchToGame(t.getTableId(), action);
			}
		}
	}
}