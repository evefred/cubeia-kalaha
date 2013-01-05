package net.kalaha.web.action;

import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.SessionManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.web.AuthFilter;
import net.sourceforge.stripes.action.ActionBeanContext;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class KalahaBeanContext extends ActionBeanContext {

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
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public String getFacebookAppId() {
		return appId;
	}

	public User getCurrentUser() {
		return (User) getSessionAttribute(AuthFilter.USER_ATTR);
	}
	
	public Session getCurrentSession() {
		return (Session) getSessionAttribute(AuthFilter.SESSION_ATTR);
	}
	
	public int getOperatorId() {
		return operatorId;
	}
	
	// --- PRIVATE METHODS --- //
	
	private Object getSessionAttribute(String name) {
		return getRequest().getSession().getAttribute(name);
	}
	
	/*private void setSessionAttribute(String name, Object o) {
		getRequest().getSession().setAttribute(name, o);
	}*/
}
