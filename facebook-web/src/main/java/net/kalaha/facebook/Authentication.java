package net.kalaha.facebook;

import javax.security.auth.login.FailedLoginException;

import net.kalaha.data.manager.UserManager;
import net.kalaha.entities.Session;

import org.apache.wicket.Request;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookParam;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.inject.Inject;
import com.google.inject.name.Named;

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
	
	Authentication() {
		FacebookXmlRestClient.initJaxbSupport();
	}
	
    public void authenticateClient(Request request, FacebookSession session) throws FailedLoginException, FacebookException {
        String authToken = request.getParameter("auth_token");
        String sessionKey = request.getParameter(FacebookParam.SESSION_KEY.toString());
        FacebookXmlRestClient fbClient = null;
        if (sessionKey != null) {
            fbClient = new FacebookXmlRestClient(apiKey, secretKey, sessionKey);
        } else if (authToken != null) {
            fbClient = new FacebookXmlRestClient(apiKey, secretKey);
            fbClient.auth_getSession(authToken);
        } else {
            throw new FailedLoginException("Session key not found");
        }
        fbClient.setIsDesktop(false);
        session.setFacebookClient(fbClient);
    }

	public void authenticateSite(FacebookSession ses) {
		String extId = String.valueOf(ses.getFacebookId());
		Session token = users.getSessionByExternalId(extId, operatorId);
		ses.setSession(token);
	}
}
