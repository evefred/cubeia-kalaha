package net.kalaha.user.impl;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginLocatorImpl implements LoginLocator {
	
	private static final int BOT_OPERATOR = 666;
	private static final int LOCAL_OPERATOR = 0;
	private static final int FACEBOOK_OPERATOR = 1;

	private static final LoginHandler NULL_HANDLER = new LoginHandler() {
		
		@Override
		public LoginResponseAction handle(LoginRequestAction arg0) {
			return new LoginResponseAction(false, -1);
		}
	};
	
	private static final boolean DEF_ALLOW_LOCAL = false;
	private static final boolean DEF_ALLOW_BOTS = false;

	@Inject
	private KalahaLoginHandler realHandler;
	
	//@Inject
	//private TrivialLoginHandler trivialHandler;
	
	@Inject
	private LocalLoginHandler localHandler;
	
	@Inject
	private BotLoginHandler botHandler;
	
	@Inject(optional=true)
	@Named("service.users.allow-bots")
	private boolean allowBots = DEF_ALLOW_BOTS;
	
	@Inject(optional=true)
	@Named("service.users.allow-local")
	private boolean allowLocal = DEF_ALLOW_LOCAL;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public void init(ServiceRegistry arg0) { }

	@Override
	public LoginHandler locateLoginHandler(LoginRequestAction req) {
		if(req.getOperatorid() == FACEBOOK_OPERATOR) {
			log.debug("Using facebook login handler");
			return realHandler;
		} else if(req.getOperatorid() == LOCAL_OPERATOR && allowLocal) {
			log.debug("Using local login handler");
			return localHandler;
		} else if(req.getOperatorid() == BOT_OPERATOR && allowBots) {
			log.debug("Using bot login handler");
			return botHandler;
		}
		log.debug("Null handler for operator id: " + req.getOperatorid());
		return NULL_HANDLER;
	}
}
