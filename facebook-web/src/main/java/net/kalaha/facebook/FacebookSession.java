package net.kalaha.facebook;

import net.kalaha.entities.Session;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;

public class FacebookSession extends WebSession {

	private static final long serialVersionUID = -5729324170190182274L;

	private FacebookXmlRestClient facebookClient;

	private long facebookId;
	private Session session;
	
	public FacebookSession(Request request) {
		super(request);
	}
	
	public long getFacebookId() {
		return facebookId;
	}
	
	public FacebookXmlRestClient getFacebookClient() {
		return facebookClient;
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
