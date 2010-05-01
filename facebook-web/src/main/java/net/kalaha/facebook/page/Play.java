package net.kalaha.facebook.page;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.restfb.FacebookException;

import net.kalaha.facebook.BasePage;

public class Play extends BasePage {
	
	@Inject
	@Named("firebase-host")
	private String firebaseHost;

	@Inject
	@Named("firebase-port")
	private int firebasePort;

	
	public Play(PageParameters pars) {
		super(pars);
		try {
			add(new Label("userName", getSession().getFBUser().name));
			add(new Label("fbUserId", String.valueOf(getSession().getFBUser().uid)));
			add(new Label("sessionId", getSession().getSession().getId()));
			add(new Label("userId", String.valueOf(getSession().getUserId())));
			add(new Label("gameId", pars.getString("gameId")));
			add(new Label("firebaseHost", firebaseHost));
			add(new Label("firebasePort", String.valueOf(firebasePort)));
		} catch (FacebookException e) {
			Logger.getLogger(getClass()).error("Facebook error", e);
		}
	}
}
