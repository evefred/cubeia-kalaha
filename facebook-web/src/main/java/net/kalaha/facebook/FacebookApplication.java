package net.kalaha.facebook;

import javax.security.auth.login.FailedLoginException;

import net.kalaha.data.manager.UserManager;
import net.kalaha.facebook.page.Index;
import net.kalaha.facebook.page.Invite;
import net.kalaha.facebook.page.Play;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RedirectToUrlException;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.authorization.strategies.page.AbstractPageAuthorizationStrategy;
import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.restfb.FacebookException;

public class FacebookApplication extends WebApplication implements IUnauthorizedComponentInstantiationListener {    

    @Inject
    private Authentication authenticator;
    
    @Inject
    @Named("facebook-api-key")
    private String apiKey;
    
    private Logger log = Logger.getLogger(getClass());
    
    @Inject
    private UserManager userManager;
    
    @Override
    protected void init() {
    	super.init();
    	setup();
    }
    
	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new FacebookSession(request, userManager);
	}

	@Override
	public void onUnauthorizedInstantiation(Component comp) {
        if (comp instanceof Page) {
        	Page page = (Page)comp;
        	handleAuthentication(page);
        } else {
            throw new UnauthorizedInstantiationException(comp.getClass());
        }
	}
    
    
	// --- PRIVATE METHODS --- //
	
    private void forceLogin(Page page) {
    	throw new RedirectToUrlException("http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0");
    }
    
	private void handleAuthentication(Page page) {
		FacebookSession session = (FacebookSession) page.getSession();
		if(!session.isAuthenticated()) {
			try {
				authenticator.authenticateClient(page.getRequest(), session);
				authenticator.authenticateSite(session);
			} catch(FailedLoginException e) {
				e.printStackTrace();
				forceLogin(page);
			} catch(FacebookException e) {
				log.error("Failed to create facbook client", e);
			} 
		}
	}
	
	private void setup() {
		mountBookmarkablePage("/Play", Play.class);
		mountBookmarkablePage("/Invite", Invite.class);
		getSecuritySettings().setAuthorizationStrategy(new FaceBookAuthorizationStrategy());
        getSecuritySettings().setUnauthorizedComponentInstantiationListener(this);
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	private static class FaceBookAuthorizationStrategy extends AbstractPageAuthorizationStrategy {

		@SuppressWarnings("unchecked")
		protected boolean isPageAuthorized(Class pageClass) {
            return false;
        }
    }
}
