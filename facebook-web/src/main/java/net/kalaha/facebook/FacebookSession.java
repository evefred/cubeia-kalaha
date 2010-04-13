package net.kalaha.facebook;

import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Session;
import net.kalaha.entities.User;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;

public class FacebookSession extends WebSession {

	private static final long serialVersionUID = -5729324170190182274L;

	private FacebookXmlRestClient facebookClient;

	private long facebookId;
	private Session session;

	private final UserManager userManager;
	
	public FacebookSession(Request request, UserManager userManager) {
		super(request);
		this.userManager = userManager;
	}
	
	public long getFacebookId() {
		return facebookId;
	}
	
	public FacebookXmlRestClient getFacebookClient() {
		return facebookClient;
	}
	
	public int getUserId() {
		return session.getUserId();
	}
	
	public User getUser() {
		int uid = session.getUserId();
		return userManager.getUser(uid);
	}
	
	public void setFacebookClient(FacebookXmlRestClient facebookClient) throws FacebookException {
		this.facebookClient = facebookClient;
		this.emergeUserId();
	}

	public boolean isAuthenticated() {
		return facebookClient != null;
	}

	public void setSession(Session ses) {
		this.session = ses;
	}
	
	public Session getSession() {
		return session;
	}
	
	// --- PRIVATE METHODS --- //
	
	private void emergeUserId() throws FacebookException {
		if(facebookClient != null) {
			this.facebookId = facebookClient.users_getLoggedInUser();
		} else {
			this.facebookId = -1L;
		}
	}
}
