package net.kalaha.facebook;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LoginRedirect extends WebPage {
	
	@Inject
    @Named("facebook-api-key")
    private String apiKey;
	
	public LoginRedirect(PageParameters p) {
		setStatelessHint(true);
	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		container.getHeaderResponse().renderJavascript("top.location=\"http://www.facebook.com/login.php?api_key=" + apiKey + "&v=1.0\";", "loginRedirect");
	}
}
