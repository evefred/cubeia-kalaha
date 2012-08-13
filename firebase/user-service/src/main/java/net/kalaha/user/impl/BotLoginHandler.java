package net.kalaha.user.impl;

import org.apache.log4j.Logger;

import net.kalaha.data.entities.User;
import net.kalaha.data.manager.UserManager;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.google.inject.Inject;

public class BotLoginHandler implements LoginHandler {
	
	@Inject
	private UserManager manager;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public LoginResponseAction handle(LoginRequestAction req) {
		log.debug("Handling login request for user: " + req.getUser());
		User u = manager.authBot(req.getUser(), Integer.parseInt(req.getPassword()));
		if(u == null) {
			log.error("Failed to login or create bot: " + req.getUser());
			return new LoginResponseAction(false, -1);
		} else {
			// TODO USER ID -> INT
			log.debug("Bot " + req.getUser() + " logged in as user id: " + u.getId());
			return new LoginResponseAction(true, (int) u.getId());
		}
	}
}
