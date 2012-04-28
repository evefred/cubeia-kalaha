package net.kalaha.web;

import net.kalaha.web.comp.ChallengeForm;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation("USER")
public class FacebookChallenge extends FacebookBasePage {
	
	public FacebookChallenge(PageParameters p) {
		super(p);
		setup();
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void setup() {
		add(new ChallengeForm("challenge-form", gameManager, userManager, true));
	}
}
