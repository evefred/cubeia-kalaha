package net.kalaha.web;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class FacebookBasePage extends WebPage {
	
	@Inject
	@Named("facebook-app-id")
	private String appId;
	
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

	protected FacebookBasePage(PageParameters p) {
		super(p);
		setup();
	}
	
	public KalahaSession getKalahaSession() {
		return (KalahaSession) getSession();
	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		super.renderHead(container);
		renderJsConfig(container);
	}
	
	
	// --- PRIVATE METHODS --- ///

	private void setup() {
		KalahaSession ses = getKalahaSession();
		setupAlertPanel(ses);
		// setupMenuPanel(ses);
	}

	private void renderJsConfig(HtmlHeaderContainer container) {
		String js = "__appId=\"" + appId + "\";";
		container.getHeaderResponse().renderJavascript(js, "appConf");
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
}
