package net.kalaha.facebook;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.kalaha.data.manager.GameManager;
import net.kalaha.entities.Session;
import net.kalaha.entities.User;
import net.kalaha.facebook.util.FacebookUtil;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class BasePage extends WebPage {
	
    private static final String DEV_VERSION = "_development";

	/*@Inject
    @Named("facebook-app-id")
    private String appId;*/
    
    @Inject
    @Named("css-version")
    private String cssVersion;
    
    @Inject
    @Named("firebase-host")
    private String firebaseHost;
    
    @Inject
    @Named("firebase-port")
    private int firebasePort;
    
    @Inject
    @Named("facebook-operator-id")
    private int operatorId;

    @Inject
    protected GameManager gameManager;
    
	public BasePage(PageParameters parameters) {
		super(parameters);
		setup();
	}

	public FacebookSession getSession() {
		return (FacebookSession) super.getSession();
	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		super.renderHead(container);
		// container.getHeaderResponse().renderCSSReference(
		// renderApiKey(container);
		renderJsConfig(container);
	}


	// --- PROTECTED METHODS --- //
	
	protected void addFBAttribute(String compId, String attr, String val) {
		FacebookUtil.setOrReplaceAttribute(this, compId, attr, val);
	}
	
	protected void addFBAttribute(MarkupContainer cont, String compId, String attr, String val) {
		FacebookUtil.setOrReplaceAttribute(cont, compId, attr, val);
	}
	
	protected User getCurrentUser() {
		return getSession().getUser();
	}
	
	protected String formatDate(Date date) {
		return new SimpleDateFormat("EEE, d MMM yyyy").format(date);
	}
	
	protected String formatDate(long date) {
		return formatDate(new Date(date));
	}
	
	
	// --- PRIVATE METHODS --- //
	
	/*private void renderApiKey(HtmlHeaderContainer container) {
		container.getHeaderResponse().renderJavascript("__API_KEY = \"" + appId + "\";", "apiKey");
	}*/
	
	
	private void renderJsConfig(HtmlHeaderContainer container) {
		Session dbSession = getSession().getSession();
		String js = "__session=\"" + dbSession.getId() + "\";" +
				"__operatorId=" + operatorId + ";" +
				"__firebaseHost=\"" + firebaseHost + "\";" +
				"__firebasePort=\"" + firebasePort + "\";";
		container.getHeaderResponse().renderJavascript(js, "jsConf");
	}
	
	private void setup() { 
		// f(getSession().getSession() == null) return; // NOT AUTH
		setStatelessHint(false);
		add(CSSPackageResource.getHeaderContribution(BasePage.class, "fb.css?v=" + getCssVersion()));
		FacebookSession ses = getSession();
		add(new Label("userName", ses.getFacebookUser().getName()));
		add(new Label("token", ses.getSession().getId()));
	}

	private String getCssVersion() {
		if(cssVersion.equals(DEV_VERSION)) {
			return String.valueOf(System.currentTimeMillis());
		} else {
			return cssVersion;
		}
	}
}
