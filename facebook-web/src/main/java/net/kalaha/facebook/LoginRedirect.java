package net.kalaha.facebook;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginRedirect extends WebPage {
	
	@Inject
    @Named("facebook-app-id")
    private String appId;
	
	@Inject
    @Named("facebook-redirect-uri")
    private String redirectUri;
	
	public LoginRedirect(PageParameters p) {
		setStatelessHint(true);
	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		container.getHeaderResponse().renderJavascript("top.location=\"https://www.facebook.com/dialog/oauth?client_id=" + appId + "&redirect_uri=" + redirectUri + "&scope=email,publish_stream", "loginRedirect");
	}
}
