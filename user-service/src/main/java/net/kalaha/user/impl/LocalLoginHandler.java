package net.kalaha.user.impl;

import net.kalaha.data.entities.User;
import net.kalaha.data.manager.UserManager;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.google.inject.Inject;

public class LocalLoginHandler implements LoginHandler {

	@Inject
	private UserManager manager;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public LoginResponseAction handle(LoginRequestAction req) {
		log.debug("Handling login request for user: " + req.getUser());
		User user = manager.authLocalUser(req.getUser(), req.getPassword());
		if(user == null) {
			log.debug("No user found for login name: " + req.getUser());
			return new LoginResponseAction(false, -1);
		} else {
			log.debug("User " + user.getId() + " found for token");
			return new LoginResponseAction(true, user.getId());
		}
	}
}
