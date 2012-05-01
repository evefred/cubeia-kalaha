package net.kalaha.web;

import net.kalaha.web.comp.FacebookIndexPanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation("USER")
public class FacebookIndex extends FacebookBasePage {

	public FacebookIndex(PageParameters p) {
		super(p);
		setup();
	}
	
	private void setup() {
		add(new FacebookIndexPanel("index-panel", gameManager));
	}
}
