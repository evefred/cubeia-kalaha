package net.kalaha.web;

import net.kalaha.web.comp.AuthenticatedIndexPanel;
import net.kalaha.web.comp.UnAuthenticatedIndexPanel;

import org.apache.wicket.PageParameters;

public class Index extends BasePage {
	
	public Index(PageParameters parameters) {
		super(parameters);
		setup();
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void setup() {
		if(!getKalahaSession().isSignedIn()) {
			add(new UnAuthenticatedIndexPanel("index-panel"));
		} else {
			add(new AuthenticatedIndexPanel("index-panel", gameManager));
		}
	}
}
