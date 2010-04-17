package net.kalaha.facebook.page;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;
import net.kalaha.facebook.BasePage;
import net.kalaha.game.logic.KalahaBoard;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Invite extends BasePage {

	@Inject
	private UserManager userManager;
	
	@Inject
	@Named("facebook-operator-id")
	private int operatorId;
	
	@Inject
	private GameManager gameManager;
	
	private Logger log = Logger.getLogger(getClass());
	
	public Invite(PageParameters pars) {
		super(pars);
		
		String extId = pars.getString("friend_selector_id");
		
		User opponent = userManager.createUser(extId, operatorId);
		
		Game game = gameManager.createGame(GameType.KALAHA, GameForm.CHALLENGE, getCurrentUser(), opponent, -1, KalahaBoard.getInitState(6));

		log.info("Invited user " + extId + "; Game " + game.getId() + " created");
		
		setResponsePage(Index.class);
		
	}
}
