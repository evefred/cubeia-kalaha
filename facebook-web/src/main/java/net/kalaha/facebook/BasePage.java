package net.kalaha.facebook;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebPage;

public class BasePage extends WebPage {

	public BasePage(PageParameters parameters) {
		super(parameters);
		setup();
	}

	public FacebookSession getFacebookSession() {
		return (FacebookSession) getSession();
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void setup() {
		add(HeaderContributor.forCss(BasePage.class, "fb.css"));	
	}
}
