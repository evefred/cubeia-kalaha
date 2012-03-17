package net.kalaha.facebook;

import net.kalaha.data.manager.UserManager;
import net.kalaha.facebook.page.Index;
import net.kalaha.facebook.page.Invite;
import net.kalaha.facebook.page.Play;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class FacebookApplication extends WebApplication {    

    @Inject
    @Named("facebook-operator-id")
    private int operatorId;
    
    // private Logger log = Logger.getLogger(getClass());
    
    @Inject
    private UserManager userManager;
    
    @Override
    protected void init() {
    	super.init();
    	reRouteUtilLog();
    	setup();
    }

	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new FacebookSession(request, userManager, operatorId);
	}

	/*@Override
	public void onUnauthorizedInstantiation(Component comp) {
        if (comp instanceof Page) {
        	Page page = (Page)comp;
        	handleAuthentication(page);
        } else {
            throw new UnauthorizedInstantiationException(comp.getClass());
        }
	}*/
    
    
	// --- PRIVATE METHODS --- //
	
	private void reRouteUtilLog() {
		SLF4JBridgeHandler.install();
	}
	
    /*private void forceLogin(Page page) {
    	// throw new RedirectToUrlException("http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0");
    	RequestCycle cycle = RequestCycle.get();
    	cycle.setResponsePage(LoginRedirect.class);
    }
    
	private void handleAuthentication(Page page) {
		FacebookSession session = (FacebookSession) page.getSession();
		if(!session.isAuthenticated()) {
			try {
				authenticator.authenticateClient(page.getRequest(), session);
				authenticator.authenticateSite(session);
			} catch(FailedLoginException e) {
				// e.printStackTrace();
				forceLogin(page);
			} catch(FacebookException e) {
				log.error("Failed to create facbook client", e);
			} 
		}
	}*/
	
	private void setup() {
		mountBookmarkablePage("/Play", Play.class);
		mountBookmarkablePage("/Invite", Invite.class);
		// getSecuritySettings().setAuthorizationStrategy(new FaceBookAuthorizationStrategy());
        // getSecuritySettings().setUnauthorizedComponentInstantiationListener(this);
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	/*private static class FaceBookAuthorizationStrategy extends AbstractPageAuthorizationStrategy {

		@SuppressWarnings({ "rawtypes" })
		protected boolean isPageAuthorized(Class pageClass) {
			if(LoginRedirect.class.equals(pageClass)) {
				return true;
			} else {
				return false;
			}
        }
    }*/
}
