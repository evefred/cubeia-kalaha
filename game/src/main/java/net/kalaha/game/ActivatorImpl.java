package net.kalaha.game;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.common.Attribute;
import com.cubeia.firebase.api.game.GameDefinition;
import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.CreationRequestDeniedException;
import com.cubeia.firebase.api.game.activator.GameActivator;
import com.cubeia.firebase.api.game.activator.RequestAwareActivator;
import com.cubeia.firebase.api.game.activator.RequestCreationParticipant;
import com.cubeia.firebase.api.game.lobby.LobbyTableAttributeAccessor;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.lobby.LobbyPath;
import com.cubeia.firebase.api.server.SystemException;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ActivatorImpl implements GameActivator, RequestAwareActivator {
	
	private final Logger log = Logger.getLogger(getClass());

	private Injector injector;
	private GameManager gameManager;

	private UserManager userManager;

	@Override
	public void destroy() { }

	@Override
	public void init(ActivatorContext context) throws SystemException {
		injector = Guice.createInjector(new ActivatorModule(context), new ManagerModule());
		gameManager = injector.getInstance(GameManager.class);
		userManager = injector.getInstance(UserManager.class);
	}

	@Override
	public void start() { }

	@Override
	public void stop() { }

	@Override
	public RequestCreationParticipant getParticipantForRequest(int pid, int seats, Attribute[] atts) throws CreationRequestDeniedException {
		int gameId = getKalahaGameId(atts);
		if(gameId == -1) {
			log.debug("Creating new game for player id: " + pid);
			GameForm form = getKalahaGameForm(atts);
			User user = userManager.getUser(pid);
			Game game = gameManager.createGame(GameType.KALAHA, form, user, null, -1);
			return new Participant(game);
		} else {
			log.debug("Ressurecting new game " + gameId + " for player id " + pid);
			final Game game = gameManager.getGame(gameId);
			if(game == null) throw new CreationRequestDeniedException(1);
			return new Participant(game);
		}
	}

	
	// --- TEST METHODS --- //

	UserManager getUserManager() {
		return userManager;
	}
	
	GameManager getGameManager() {
		return gameManager;
	}
	

	// --- PRIVATE METHODS --- //
	
	private GameForm getKalahaGameForm(Attribute[] atts) {
		for (Attribute a : atts) {
			if(a.name.equals("form")) {
				return GameForm.valueOf(a.value.getStringValue().toUpperCase());
			}
		}
		return GameForm.CHALLENGE;
	}

	private int getKalahaGameId(Attribute[] atts) {
		for (Attribute a : atts) {
			if(a.name.equals("gameId")) {
				return a.value.getIntValue();
			}
		}
		return -1;
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	private static class Participant implements RequestCreationParticipant {

		private final Game game;

		public Participant(Game game) {
			this.game = game;
		}

		@Override
		public void tableCreated(Table table, LobbyTableAttributeAccessor atts) {
			table.getGameState().setState(new net.kalaha.game.action.State(game.getState()));
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
}