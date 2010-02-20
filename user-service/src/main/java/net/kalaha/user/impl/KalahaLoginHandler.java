package net.kalaha.user.impl;

import org.apache.log4j.Logger;

import net.kalaha.user.api.UserManager;
import net.kalaha.user.api.Session;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;

public class KalahaLoginHandler implements LoginHandler {

	@Inject
	private UserManager manager;
	
	@Log4j
	private Logger log;
	
	@Override
	public LoginResponseAction handle(LoginRequestAction req) {
		String sessionId = req.getPassword();
		log.debug("Handling login request for session token: " + sessionId);
		Session ses = manager.getSessionById(sessionId);
		if(ses == null) {
			log.debug("Not session found for token: " + sessionId);
			return new LoginResponseAction(false, -1);
		} else {
			log.debug("User " + ses.getUserId() + " found for token");
			return new LoginResponseAction(true, ses.getUserId());
		}
	}
}
