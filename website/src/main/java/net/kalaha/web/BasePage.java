package net.kalaha.web;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.web.comp.AuthenticatedSessionPanel;
import net.kalaha.web.comp.UnAuthenticatedSessionPanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class BasePage extends WebPage {
	
	@Inject
	protected GameManager gameManager;

	@Inject
	protected UserManager userManager;
	
    @Inject
    @Named("firebase-host")
    protected String firebaseHost;
    
    @Inject
    @Named("firebase-port")
    protected int firebasePort;
    
    @Inject
    @Named("facebook-operator-id")
    protected int operatorId;
	
	protected BasePage(PageParameters p) {
		super(p);
		setup();
	}
	
	public KalahaSession getKalahaSession() {
		return (KalahaSession) getSession();
	}
	
	
	// --- PRIVATE METHODS --- ///

	private void setup() {
		KalahaSession ses = getKalahaSession();
		setupSessionPanel(ses);
		setupAlertPanel(ses);
		// setupMenuPanel(ses);
	}

	private void setupAlertPanel(KalahaSession ses) {
		Alert alert = ses.popAlert();
		WebMarkupContainer cont = new WebMarkupContainer("alert-box");
		if(alert != null) {
			cont.add(new AttributeAppender("class", Model.of(alert.type.getClazz()), " "));
			cont.add(new Label("alert-type-label", alert.type.getLabel()));
			cont.add(new Label("alert-msg", alert.msg));
			cont.setVisible(true);
		} else {
			cont.add(new Label("alert-type-label", ""));
			cont.add(new Label("alert-msg", ""));
			cont.setVisible(false);
		}
		add(cont);
	}

	private void setupSessionPanel(KalahaSession ses) {
		if(!ses.isSignedIn()) {
			add(new UnAuthenticatedSessionPanel("session-panel"));
		} else {
			add(new AuthenticatedSessionPanel("session-panel"));
		}
	}
}
