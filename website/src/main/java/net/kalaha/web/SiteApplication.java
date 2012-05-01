package net.kalaha.web;

import static net.kalaha.web.facebook.AuthFilter.AUTH_TOKEN;

import javax.servlet.http.HttpSession;

import net.kalaha.data.manager.UserManager;
import net.kalaha.web.facebook.AuthToken;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.settings.ISecuritySettings;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SiteApplication extends AuthenticatedWebApplication {

    @Inject
    @Named("facebook-operator-id")
    private int operatorId;
    
    // private Logger log = Logger.getLogger(getClass());
    
    @Inject
    private UserManager userManager;
	
	@Override
	protected void init() {
		reRouteUtilLog();
		super.init();
		setup();
	}
	
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}
	
	@Override
	public Session newSession(Request request, Response response) {
		return new KalahaSession(request, userManager, operatorId);
	}
	
	@Override
	protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
		return KalahaSession.class;
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

	
	
	// --- PRIVATE METHODS -- //
	
	private void reRouteUtilLog() {
		SLF4JBridgeHandler.install();
	}
	
	private void setup() {
		addBookmarkableLinks();
		customizeSecurity();
	}

	private void customizeSecurity() {
		ISecuritySettings sec = getSecuritySettings();
		final IAuthorizationStrategy wrapped = sec.getAuthorizationStrategy();
		sec.setAuthorizationStrategy(new IAuthorizationStrategy() {
			
			@Override
			public <T extends Component> boolean isInstantiationAuthorized(Class<T> componentClass) {
				if(checkForAuthToken()) {
					return wrapped.isInstantiationAuthorized(componentClass);
				} else {
					return false;
				}
			}
			
			@Override
			public boolean isActionAuthorized(Component component, Action action) {
				return wrapped.isActionAuthorized(component, action);
			}
			
			private boolean checkForAuthToken() {
				KalahaSession ses = (KalahaSession) RequestCycle.get().getSession();
				Request request = RequestCycle.get().getRequest();
				HttpSession session = ((WebRequest) request).getHttpServletRequest().getSession(false);
				AuthToken next = (AuthToken) session.getAttribute(AUTH_TOKEN);
				if (next != null) {
					ses.signIn(next);
				}
				return true;
			}
		});
	}

	private void addBookmarkableLinks() {
		mountBookmarkablePage("/index", Index.class);
		mountBookmarkablePage("/login", Login.class);
		mountBookmarkablePage("/facebook-web/index", FacebookIndex.class);
		mountBookmarkablePage("/facebook-web/challenge", FacebookChallenge.class);
		mountBookmarkablePage("/facebook-web/play", FacebookPlay.class);
		mountBookmarkablePage("/challenge", Challenge.class);
		mountBookmarkablePage("/play", Play.class);
	}
}
