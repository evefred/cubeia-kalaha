package net.kalaha.user.impl;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;

public class LoginLocatorImpl implements LoginLocator {

	private static final int FACEBOOK = 1;

	private static final LoginHandler NULL_HANDLER = new LoginHandler() {
		
		@Override
		public LoginResponseAction handle(LoginRequestAction arg0) {
			return new LoginResponseAction(false, -1);
		}
	};

	@Inject
	private KalahaLoginHandler realHandler;
	
	//@Inject
	//private TrivialLoginHandler trivialHandler;
	
	@Inject
	private LocalLoginHandler localHandler;
	
	@Log4j
	private Logger log;
	
	@Override
	public void init(ServiceRegistry arg0) { }

	@Override
	public LoginHandler locateLoginHandler(LoginRequestAction req) {
		if(req.getOperatorid() == FACEBOOK) {
			log.debug("Using facebook login handler");
			return realHandler;
		} else if(req.getOperatorid() == 0) {
			log.debug("Using local login handler");
			return localHandler;
		} 
		log.debug("Null handler for operator id: " + req.getOperatorid());
		return NULL_HANDLER;
	}
}
