package net.kalaha.facebook.page;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;

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

	@Inject
	@Named("facebook-operator-id")
	private int operatorId;

	private String gameId;
	
	public Play(PageParameters pars) {
		super(pars);
		gameId = pars.getString("gameId");
		add(JavascriptPackageResource.getHeaderContribution("AC_OETags.js"));
		try {
			add(new Label("userName", getSession().getFBUser().name));
			add(new Label("fbUserId", String.valueOf(getSession().getFBUser().uid)));
			add(new Label("sessionId", getSession().getSession().getId()));
			add(new Label("userId", String.valueOf(getSession().getUserId())));
			add(new Label("gameId", gameId));
			add(new Label("firebaseHost", firebaseHost));
			add(new Label("firebasePort", String.valueOf(firebasePort)));
		} catch (FacebookException e) {
			Logger.getLogger(getClass()).error("Facebook error", e);
		}
	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		super.renderHead(container);
		renderFlashVersions(container);
		renderFirebaseInfo(container);
	}
	
	
	// --- PRIVATE METHODS --- //

	private void renderFirebaseInfo(HtmlHeaderContainer container) {
		try {
			container.getHeaderResponse().renderJavascript("__FIREBASE_HOST = \"" + firebaseHost + "\"; " +
					"__FIREBASE_PORT = \"" + String.valueOf(firebasePort) + "\"; " +
					"__USER_NAME = \"" + URLEncoder.encode(getSession().getFBUser().name, "UTF-8") + "\"; " +
					"__SESSION_TOKEN = \"" + getSession().getSession().getId() + "\"; " +
					"__OPERATOR_ID = \"" + String.valueOf(operatorId) + "\"; " +
					"__GAME_ID = \"" + gameId + "\";", "firebaseInfo");	
		} catch (FacebookException e) {
			Logger.getLogger(getClass()).error("Facebook error", e);
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger(getClass()).error("Missing UTF-8?!", e);
		}
	}

	private void renderFlashVersions(HtmlHeaderContainer container) {
		container.getHeaderResponse().renderJavascript("var requiredMajorVersion = 9; var requiredMinorVersion = 0; var requiredRevision = 124;", "flashVersions");
	}
}
