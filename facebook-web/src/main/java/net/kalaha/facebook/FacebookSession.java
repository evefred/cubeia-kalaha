package net.kalaha.facebook;

import static net.kalaha.facebook.AuthFilter.AUTH_TOKEN;

import javax.servlet.http.HttpSession;

import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Session;
import net.kalaha.entities.User;
import net.kalaha.facebook.page.fb.FacebookUser;

import org.apache.log4j.Logger;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebSession;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;

public class FacebookSession extends WebSession {

	private static final long serialVersionUID = -5729324170190182274L;
	private static final Logger log = Logger.getLogger(FacebookSession.class);
	
	private FacebookClient facebookClient;
	private UserManager userManager;
	
	private Session session;
	private AuthToken token;
	private int operatorId;
	private FacebookUsers users;
	
	public FacebookSession(Request request, UserManager userManager, int operatorId, FacebookUsers users) {
		super(request);
		this.userManager = userManager;
		this.operatorId = operatorId;
		this.users = users;
		checkCreateClient(request);
	}

	public long getFacebookId() {
		return Long.parseLong(getFacebookUser().getId());
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

	public boolean isAuthenticated() {
		return facebookClient != null;
	}

	public Session getSession() {
		return session;
	}
	
	public FacebookUser getFacebookUser() {
		return users.get("me");
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void checkCreateClient(Request request) {
		HttpSession session = ((WebRequest) request).getHttpServletRequest().getSession(false);
		AuthToken next = (AuthToken) session.getAttribute(AUTH_TOKEN);
		if(token == null || !token.token.equals(next.token)) {
			log.debug("Creating new Facebook client for token: " + next.token);
			facebookClient = new DefaultFacebookClient(next.token);
			this.users.setClient(facebookClient);
			String facebookId = getFacebookUser().getId();
			this.session = userManager.getSessionByExternalId(facebookId, operatorId);
			this.token = next;
		}
	}
}
