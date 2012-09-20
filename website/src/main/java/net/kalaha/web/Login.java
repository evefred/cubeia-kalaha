package net.kalaha.web;

import static net.kalaha.web.facebook.OAuthFilter.AUTH_TOKEN;

import javax.servlet.http.HttpSession;

import net.kalaha.web.comp.LoginForm;
import net.kalaha.web.facebook.AuthToken;

import org.apache.wicket.PageParameters;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.protocol.http.WebRequest;

public class Login extends BasePage {
	
	public Login(PageParameters p) {
		super(p);
		setup();
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void setup() {
		checkForAuthToken();
		if(getKalahaSession().isSignedIn()) {
			throw new RestartResponseException(Index.class);
		} else {
			add(new LoginForm("login-form"));
		}	
	}


	private void checkForAuthToken() {
		Request request = RequestCycle.get().getRequest();
		HttpSession session = ((WebRequest) request).getHttpServletRequest().getSession(false);
		AuthToken next = (AuthToken) session.getAttribute(AUTH_TOKEN);
		if(next != null) {
			KalahaSession ses = getKalahaSession();
			ses.signIn(next);
		}
	}
}
