package net.kalaha.web.action;

import static net.kalaha.web.auth.AuthFilter.AUTH_TOKEN_ATTR;
import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.SessionManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.web.auth.AuthToken;
import net.sourceforge.stripes.action.ActionBeanContext;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

public class KalahaBeanContext extends ActionBeanContext {
	
	private static final String USER_ATTR = "_user";
	private static final String SESSION_ATTR = "_session";
	private static final String CLIENT_ATTR = "_client";	
	
	private final Logger log = Logger.getLogger(getClass());

	@Inject
	@Named("facebook-app-id")
	private String appId;
	
	@Inject
	private UserManager userManager;

	@Inject
	private SessionManager sessionManager;
	
	@Inject
    @Named("facebook-operator-id")
    private int operatorId;
	
	public AuthToken getAuthToken() {
		return (AuthToken) getSessionAttribute(AUTH_TOKEN_ATTR);
	}

	public String getFacebookAppId() {
		return appId;
	}

	public User getCurrentUser() {
		User user = (User) getSessionAttribute(USER_ATTR);
		if(user == null) {
			AuthToken token = getAuthToken();
			// create client and get facebook user
			log.debug("Creating new Facebook client for token: " + token);
			FacebookClient client = new DefaultFacebookClient(token.getToken());
			FacebookUser fbuser = client.fetchObject("me", FacebookUser.class);
			// get current session and fetch user
			Session session = sessionManager.getSessionByExternalId(fbuser.getId(), operatorId);
			log.debug("Found session for client: " + session);
			user = userManager.getUser(session.getUserId());
			// update display name
			userManager.setDisplayName(user.getId(), fbuser.getName());
			// store objects in session
			setSessionAttribute(SESSION_ATTR, session);
			setSessionAttribute(CLIENT_ATTR, client);
			setSessionAttribute(USER_ATTR, user);
			
		}
		return user;
	}
	
	public Session getCurrentSession() {
		return (Session) getSessionAttribute(SESSION_ATTR);
	}
	
	public int getOperatorId() {
		return operatorId;
	}
	
	// --- PRIVATE METHODS --- //
	
	private Object getSessionAttribute(String name) {
		return getRequest().getSession().getAttribute(name);
	}
	
	private void setSessionAttribute(String name, Object o) {
		getRequest().getSession().setAttribute(name, o);
	}
}
