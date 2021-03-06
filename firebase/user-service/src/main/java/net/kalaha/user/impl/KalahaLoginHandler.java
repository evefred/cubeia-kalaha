package net.kalaha.user.impl;

import net.kalaha.data.entities.Session;
import net.kalaha.data.manager.SessionManager;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.google.inject.Inject;

public class KalahaLoginHandler implements LoginHandler {

	private final Logger log = Logger.getLogger(getClass());
	
	@Inject
	private SessionManager sessions;
	
	@Override
	public LoginResponseAction handle(LoginRequestAction req) {
		String sessionId = req.getPassword();
		log.debug("Handling login request for session token: " + sessionId);
		Session ses = sessions.getSessionById(sessionId);
		if(ses == null) {
			log.debug("No session found for token: " + sessionId);
			return new LoginResponseAction(false, -1);
		} else {
			// TODO USER ID -> INT?
			log.debug("User " + ses.getUserId() + " found for token");
			return new LoginResponseAction(true, (int) ses.getUserId());
		}
	}
}
