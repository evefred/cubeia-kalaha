package net.kalaha.facebook;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.kalaha.data.manager.GameManager;
import net.kalaha.entities.User;
import net.kalaha.facebook.util.FBUtil;

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

	@Inject
    @Named("facebook-api-key")
    private String apiKey;
    
    @Inject
    @Named("css-version")
    private String cssVersion;

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
		renderApiKey(container);
	}
	
	
	// --- PROTECTED METHODS --- //
	
	protected void addFBAttribute(String compId, String attr, String val) {
		FBUtil.setOrReplaceAttribute(this, compId, attr, val);
	}
	
	protected void addFBAttribute(MarkupContainer cont, String compId, String attr, String val) {
		FBUtil.setOrReplaceAttribute(cont, compId, attr, val);
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
	
	private void renderApiKey(HtmlHeaderContainer container) {
		container.getHeaderResponse().renderJavascript("__API_KEY = \"" + apiKey + "\";", "apiKey");
	}
	
	private void setup() { 
		setStatelessHint(false);
		add(CSSPackageResource.getHeaderContribution(BasePage.class, "fb.css?v=" + getCssVersion()));
		FacebookSession ses = getSession();
		long fbId = ses.getFacebookId();	
		addFBAttribute("ownerId", "uid", String.valueOf(fbId));
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
