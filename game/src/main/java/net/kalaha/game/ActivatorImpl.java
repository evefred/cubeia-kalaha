package net.kalaha.game;

import static net.kalaha.entities.GameForm.CHALLENGE;
import static net.kalaha.entities.GameStatus.FINISHED;
import static net.kalaha.entities.GameType.KALAHA;

import java.util.HashMap;
import java.util.Map;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Game;
import net.kalaha.entities.User;
import net.kalaha.game.logic.KalahaBoard;
import net.kalaha.table.api.CreateGameRequest;
import net.kalaha.table.api.CreateGameResponse;
import net.kalaha.table.api.GetTableRequest;
import net.kalaha.table.api.GetTableResponse;
import net.kalaha.table.api.TableManager;
import net.kalaha.table.api.TableRequestAction;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.game.GameDefinition;
import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.GameActivator;
import com.cubeia.firebase.api.game.activator.RequestCreationParticipant;
import com.cubeia.firebase.api.game.activator.TableFactory;
import com.cubeia.firebase.api.game.lobby.LobbyTable;
import com.cubeia.firebase.api.game.lobby.LobbyTableAttributeAccessor;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.lobby.LobbyPath;
import com.cubeia.firebase.api.routing.ActivatorAction;
import com.cubeia.firebase.api.routing.RoutableActivator;
import com.cubeia.firebase.api.server.SystemException;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ActivatorImpl implements GameActivator, /*RequestAwareActivator,*/ RoutableActivator {
	
	private final Logger log = Logger.getLogger(getClass());

	private Injector injector;
	private GameManager gameManager;
	private UserManager userManager;
	
	private Mapping mapping = new Mapping();
	private ActivatorContext context;

	@Override
	public void destroy() { }

	@Override
	public void init(ActivatorContext context) throws SystemException {
		injector = Guice.createInjector(new ActivatorModule(context), new ManagerModule());
		gameManager = injector.getInstance(GameManager.class);
		userManager = injector.getInstance(UserManager.class);
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
	public void start() { }

	@Override
	public void stop() { }
	

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
		Game game = gameManager.createGame(KALAHA, CHALLENGE, user, opponent, -1, KalahaBoard.getInitState(6));
		int tableId = getTableForGame(game.getId(), q.getUserId());
		if(tableId != -1) {
			dispatch(new CreateGameResponse(q, game.getId(), tableId));
		} else {
			// TODO: Handle failure
		}
	}
	
	private void handleGetTable(GetTableRequest q) {
		int tableId = getTableForGame(q.getGameId(), q.getUserId());
		if(tableId != -1) {
			dispatch(new GetTableResponse(q, tableId));
		} else {
			// TODO: Handle failure
		}
	}

	private int getTableForGame(int gameId, int userId) {
		int tableId = -1;
		synchronized(mapping) {
			tableId = mapping.getTableForGame(gameId);
			if(tableId == -1) {
				tableId = createTable(gameId, userId);
			}
		}
		return tableId;
	}
	
	private void dispatch(TableRequestAction action) {
		TableManager manager = context.getServices().getServiceInstance(TableManager.class);
		manager.sendToClient(action);
	}

	private int createTable(int gameId, int userId) {
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
		LobbyTable table = fact.createTable(2, new Participant(game));
		log.debug("Table " + table.getTableId() + " created for game " + gameId + "; on behalf of player " + userId);
		mapping.put(gameId, table.getTableId());
		return table.getTableId();
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	private static class Participant implements RequestCreationParticipant {

		private final Game game;

		public Participant(Game game) {
			this.game = game;
		}

		@Override
		public void tableCreated(Table table, LobbyTableAttributeAccessor atts) {
			table.getGameState().setState(new KalahaBoard(game));
			atts.setIntAttribute("gameId", game.getId());
		}
		
		@Override
		public String getTableName(GameDefinition arg0, Table table) {
			return "Kalaha [game: " + game.getId() + ", table: " + table.getId() + "]";
		}
		
		@Override
		public LobbyPath getLobbyPathForTable(Table table) {
			return new LobbyPath(table.getMetaData().getGameId(), game.getForm().toString().toLowerCase(), table.getId());
		}
		
		@Override
		public boolean reserveSeatsForInvitees() {
			return true;
		}
		
		@Override
		public int[] modifyInvitees(int[] invitees) {
			return invitees;
		}
	}
	
	private static class Mapping {
		
		private Map<Integer, Integer> gameToTable = new HashMap<Integer, Integer>();
		private Map<Integer, Integer> tableToGame = new HashMap<Integer, Integer>();
	
		public void put(int gameId, int tableId) {
			gameToTable.put(gameId, tableId);
			tableToGame.put(tableId, gameId);
		}
		
		public int getTableForGame(int gameId) {
			Integer i = gameToTable.get(gameId);
			return (i == null ? -1 : i.intValue());
		}
		
		@SuppressWarnings("unused")
		public void removeForTable(int tableId) {
			Integer i = tableToGame.remove(tableId);
			if(i != null) {
				gameToTable.remove(i);
			}
		}
	}
}