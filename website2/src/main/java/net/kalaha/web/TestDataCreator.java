package net.kalaha.web;

import static net.kalaha.data.entities.GameType.KALAHA;
import static net.kalaha.game.logic.KalahaBoard.getInitState;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class TestDataCreator {

	private final Logger log = Logger.getLogger(getClass());
	
	@Inject
	private GameManager gameManager;
	
	@Inject
	private UserManager userManager;
	
	public void create() {
		User bot1 = userManager.getUserByLocalName("Bot_1");
		if(bot1 == null) {
			log.info("Creating bot user 1");
			bot1 = userManager.createLocalUser("Bot_1", "password");
			User lars = userManager.getUserByExternalId("670222688", 1); // Lars J. Nilsson
			log.info("Setting up a couple of games");
			gameManager.createGame(KALAHA, lars, bot1, -1, getInitState(6));
			gameManager.createGame(KALAHA, bot1, lars, -1, getInitState(6));
		}
	}
}
