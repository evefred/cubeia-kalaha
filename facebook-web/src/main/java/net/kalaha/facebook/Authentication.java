package net.kalaha.facebook;

import javax.security.auth.login.FailedLoginException;

import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Session;
import net.kalaha.facebook.page.fb.FBSession;

import org.apache.log4j.Logger;
import org.apache.wicket.Request;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookException;
import com.restfb.Parameter;

public class Authentication {

	@Inject
	@Named("facebook-api-key")
	private String apiKey;
	
	@Inject
	@Named("facebook-secret-key")
	private String secretKey;
	
	@Inject
	@Named("facebook-operator-id")
	private int operatorId;

	@Inject
	private UserManager users;
	
	private final Logger log = Logger.getLogger(getClass());
	
    public void authenticateClient(Request request, FacebookSession session) throws FailedLoginException, FacebookException {
        String authToken = request.getParameter("auth_token");
        String sessionKey = request.getParameter("fb_sig_session_key");
        if(sessionKey == null && authToken == null) throw new FailedLoginException(); // NOT AUTHORIZED
        log.info("Authenticating token " + authToken + " for session " + sessionKey);
        FacebookClient fbClient = new DefaultFacebookClient(apiKey, secretKey);
        if(sessionKey == null) {
        	FBSession s = fbClient.execute("auth.getSession", FBSession.class, 
        						Parameter.with("auth_token ", authToken));
        	
        	sessionKey = s.session_key;
        }
        session.setFacebookClient(fbClient, sessionKey);
    }

	public void authenticateSite(FacebookSession ses) {
		String extId = String.valueOf(ses.getFacebookId());
		Session token = users.getSessionByExternalId(extId, operatorId);
		ses.setSession(token);
	}
}
