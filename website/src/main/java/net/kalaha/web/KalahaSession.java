package net.kalaha.web;

import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Session;
import net.kalaha.entities.User;
import net.kalaha.web.facebook.AuthToken;
import net.kalaha.web.facebook.FacebookUser;

import org.apache.log4j.Logger;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;

import com.restfb.DefaultFacebookClient;

public class KalahaSession extends AuthenticatedWebSession {

	private static final long serialVersionUID = 4785954978938351198L;
	private static final Logger log = Logger.getLogger(KalahaSession.class);

	private final UserManager userManager;
	private final int operatorId;

	private User user;
	private Alert alert;
	private Session session;

	private AuthToken externalToken;
	
	private String displayName;

	public KalahaSession(Request request, UserManager userManager, int operatorId) {
		super(request);
		this.userManager = userManager;
		this.operatorId = operatorId;
	}

	@Override
	public boolean authenticate(String username, String password) {
		user = userManager.authLocalUser(username, password);
		if(user != null) {
			this.session = userManager.getSessionByUserId(user.getId());
			this.displayName = username;
		}
		return user != null;
	}
	
	public boolean signIn(AuthToken next) {
		if(externalToken == null || !externalToken.token.equals(next.token)) {
			log.debug("Creating new Facebook client for token: " + next.token);
			DefaultFacebookClient client = new DefaultFacebookClient(next.token);
			// this.users.setClient(facebookClient);
			FacebookUser fbuser = client.fetchObject("me", FacebookUser.class);
			String facebookId = fbuser.getId();
			this.session = userManager.getSessionByExternalId(facebookId, operatorId);
			this.user = userManager.getUser(this.session.getUserId());
			this.externalToken = next;
			this.displayName = fbuser.getName();
			userManager.setDisplayName(user.getId(), displayName);
		}
		super.signIn(true);
		return true;
	}

	@Override
	public Roles getRoles() {
		if(isSignedIn()) {
			return new Roles("USER");
		} else {
			return null;
		}
	}

	public String getDisplayName() {
		checkAuthenticated();
		return displayName;
	}
	
	public Session getSession() {
		checkAuthenticated();
		return session;
	}
	
	public User getUser() {
		checkAuthenticated();
		return user;
	}
	
	public void setAlert(Alert alert) {
		this.alert = alert;
	}
	
	public Alert popAlert() {
		Alert tmp = this.alert;
		this.alert = null;
		return tmp;
	}
	
	
	// --- PRIVATE METHODS --- //

	private void checkAuthenticated() {
		if(user == null) {
			throw new IllegalStateException("not logged in");
		}
	}
}
