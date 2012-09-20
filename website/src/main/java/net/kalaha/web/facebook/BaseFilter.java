package net.kalaha.web.facebook;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.kalaha.common.guice.ClassPathPropertiesModule;

import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

public abstract class BaseFilter implements Filter {

	public static final String AUTH_TOKEN = "_authToken";

	protected final Logger log = Logger.getLogger(getClass());

	protected static final String DIALOG_URL = "https://www.facebook.com/dialog/oauth";
	protected static final String AUTH_URL = "https://graph.facebook.com/oauth/access_token";

	protected static final String UTF_8 = "UTF-8";
	protected static final String CODE = "code";
	protected static final String ERROR = "error";
	protected static final String REDIRECT = "_redirect";
	
	@Inject
	@Named("facebook-app-id")
	protected String appId;
	
	@Inject
	@Named("facebook-app-secret")
	protected String appSecret;
	
	@Inject
    @Named("facebook-redirect-uri")
	protected String fbRedirectUri;
	
	@Inject
    @Named("facebook-redirect-final")
	protected String fbFinalredirectUri;
	
	@Inject
    @Named("facebook-app-redirect-uri")
	protected String appRedirectUri;

	@Inject
    @Named("facebook-app-scope")
	protected String appScope;

	@Inject(optional=true)
    @Named("facebook-context")
	protected String facebookContext = "/facebook-web/";
	
	@Inject(optional=true)
    @Named("facebook-app-login")
	protected String forceAppLogin = "/login/facebook";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Injector injector = Guice.createInjector(new ClassPathPropertiesModule("facebook.properties"));
		injector.injectMembers(this);
	}
	
	@Override
	public void destroy() { }
	
	protected void redirect(HttpServletResponse res, String url, boolean breakFrame) throws IOException {
		log.debug("Redirecting to: " + url + "; breaking frame: " + breakFrame);
		// res.sendRedirect(url);
		if(breakFrame) {
			String tmp = "<html><head><script type='text/javascript'>top.location.href=";
			tmp += "\"" + url + "\"";
			tmp += "</script></head></html>";
			res.setContentType("text/html");
			res.getWriter().write(tmp);
			res.getWriter().close();
		} else {
			res.sendRedirect(url);
		}
	}
	
	protected boolean isFacebookReq(HttpServletRequest req) {
		String test = req.getRequestURI();
		return test != null && test.startsWith(facebookContext);
	}
	
	protected String getDialogUrl(boolean isFacebook) throws IOException {
		return DIALOG_URL + "?client_id=" + appId + "&scope=" + appScope + "&redirect_uri=" + URLEncoder.encode(isFacebook ? fbRedirectUri : appRedirectUri, UTF_8);
	}
	
	protected String getAuthUrl(String code, boolean isFacebook) throws IOException {
		return AUTH_URL + "?client_id=" + appId + "&client_secret=" + appSecret + "&code=" + code + "&redirect_uri=" + URLEncoder.encode(isFacebook ?  fbRedirectUri : appRedirectUri, UTF_8);
	}
}
