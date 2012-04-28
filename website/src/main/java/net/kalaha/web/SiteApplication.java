package net.kalaha.web;

import net.kalaha.data.manager.UserManager;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
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
