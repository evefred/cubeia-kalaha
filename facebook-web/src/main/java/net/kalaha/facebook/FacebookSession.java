package net.kalaha.facebook;

import java.util.List;

import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Session;
import net.kalaha.entities.User;
import net.kalaha.facebook.page.fb.FBUser;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.Parameter;

public class FacebookSession extends WebSession {

	private static final long serialVersionUID = -5729324170190182274L;

	private FacebookClient facebookClient;

	private long facebookId;
	private Session session;

	private final UserManager userManager;

	private String fbSessionKey;
	private FBUser facebookUser;
	
	public FacebookSession(Request request, UserManager userManager) {
		super(request);
		this.userManager = userManager;
	}
	
	public long getFacebookId() {
		return facebookId;
	}
	
	public FacebookClient getFacebookClient() {
		return facebookClient;
	}
	
	public int getUserId() {
		return session.getUserId();
	}
	
	public User getUser() {
		int uid = session.getUserId();
		return userManager.getUser(uid);
	}
	
	public void setFacebookClient(FacebookClient facebookClient, String fbSessionKey) throws FacebookException {
		this.facebookClient = facebookClient;
		this.fbSessionKey = fbSessionKey;
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
	
	public FBUser getFBUser() throws FacebookException {
		if(facebookUser == null) {
			List<FBUser> list = facebookClient.executeForList("users.getInfo",  FBUser.class, 
					Parameter.with("uids", String.valueOf(facebookId)),
					Parameter.with("fields", "name"));
			
			facebookUser = list.get(0);
		}
		return facebookUser;
	}
	
	// --- PRIVATE METHODS --- //
	
	private void emergeUserId() throws FacebookException {
		if(facebookClient != null) {
			this.facebookId = facebookClient.execute("users.getLoggedInUser", fbSessionKey, Long.class);
		} else {
			this.facebookId = -1L;
		}
	}
	
}
