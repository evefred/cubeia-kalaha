package net.kalaha.web.facebook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.kalaha.web.PropertiesModule;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

public class AuthFilter implements Filter {
	
	private static final String REDIRECT = "_redirect";
	public static final String AUTH_TOKEN = "_authToken";

	private final Logger log = Logger.getLogger(getClass());
	

	private static final String DIALOG_URL = "https://www.facebook.com/dialog/oauth";
	private static final String AUTH_URL = "https://graph.facebook.com/oauth/access_token";

	private static final String CODE = "code";
	private static final String ERROR = "error";

	@Inject
	@Named("facebook-app-id")
	private String appId;
	
	@Inject
	@Named("facebook-app-secret")
	private String appSecret;
	
	@Inject
    @Named("facebook-redirect-uri")
    private String fbRedirectUri;
	
	@Inject
    @Named("facebook-redirect-final")
    private String fbFinalredirectUri;
	
	@Inject
    @Named("facebook-app-redirect-uri")
    private String appRedirectUri;

	@Inject
    @Named("facebook-app-scope")
    private String appScope;

	@Inject(optional=true)
    @Named("facebook-context")
    private String facebookContext = "/facebook-web/";
	
	@Inject(optional=true)
    @Named("facebook-app-login")
    private String forceAppLogin = "/login/facebook";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Injector injector = Guice.createInjector(new PropertiesModule());
		injector.injectMembers(this);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest) request;
	     HttpServletResponse res = (HttpServletResponse) response;
	     if(hasToken(req)) {
	    	 log.trace("User is authenticated, forwarding to Wicket: " + req.getRequestURI());
	    	 doForward(req, res, chain, false);
	     } else if(isError(req)) {
	    	 log.info("Authentication denied, reason: " + req.getParameter("error_reason"));
	    	 clearPostAuthRedirect(req);
	     } else if(isCallback(req)) {
	    	 log.trace("Authentication call back");
	    	 if(doAuthentication(req, res)) {
	    		 log.trace("Authentication sucessful, forwarding to Wicket: " + req.getRequestURI());
		    	 doForward(req, res, chain, true);
	    	 } else {
	    		 log.trace("Authentication failed, returning nada");
	    		 clearPostAuthRedirect(req);
	    	 }
	     } else {
	    	 boolean isFacebook = isFacebookReq(req);
		     boolean isForced = isForceLogin(req);
	    	 if(isFacebook || isForced) {
		    	 log.debug("User is not authenticated, forwarding to FB auth dialog");
		    	 savePostAuthRedirect(req);
		    	 redirect(res, getDialogUrl(isFacebook), isFacebook);
	    	 } else {
	    		 log.trace("Allowing anonymous access: " + req.getRequestURI());
	    		 doForward(req, res, chain, false);
	    	 }
	     }
	}


	@Override
	public void destroy() { }
	
	
	// --- PRIVATE METHODS --- //
	
	private void doForward(HttpServletRequest req, HttpServletResponse res, FilterChain chain, boolean intoFrame) throws IOException, ServletException {
		if(isFacebookReq(req) && intoFrame) {
			redirect(res, fbFinalredirectUri, false);
		} else {
			String redir = clearPostAuthRedirect(req);
			if(redir == null) {
				chain.doFilter(req, res);
			} else {
				log.trace("Post auth redirect: " + redir);
				redirect(res, redir, false);
			}
		}
	}
	
	private void savePostAuthRedirect(HttpServletRequest req) {
		String redir = req.getRequestURI();
		log.trace("Saving URL for post auth redirect: " + redir);
		req.getSession().setAttribute(REDIRECT, redir);
	}

	private boolean isPostAuthRedirectFacebook(HttpServletRequest req) {
		String redir = (String) req.getSession().getAttribute(REDIRECT);
		return (redir != null && redir.startsWith(facebookContext));
	}
	
	private String clearPostAuthRedirect(HttpServletRequest req) {
		String redir = (String) req.getSession().getAttribute(REDIRECT);
		req.getSession().removeAttribute(REDIRECT);
		return redir;
	}
	
	private boolean isForceLogin(HttpServletRequest req) {
		String test = req.getRequestURI();
		return test != null && test.startsWith(forceAppLogin);
	}
	
	private boolean isFacebookReq(HttpServletRequest req) {
		String test = req.getRequestURI();
		return test != null && test.startsWith(facebookContext);
	}
	
	private boolean doAuthentication(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String code = req.getParameter(CODE);
		String authUrl = getAuthUrl(code, isPostAuthRedirectFacebook(req));
		log.debug("Calling FB auth on url: " + authUrl);
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(authUrl);
		try {
			int status = client.executeMethod(method);
			String resp = method.getResponseBodyAsString();
			if (status != HttpStatus.SC_OK) {
		        throw new IOException("Facebook returned status: " + status + "; body: " + resp);
		    }
			log.debug("FB auth returned: " + resp);
			Map<String, String> values = splitValues(resp);
			String token = values.get("access_token");
			String exp = values.get("expires");
			if(token != null && exp != null) {
				long expires = System.currentTimeMillis() + (Long.parseLong(exp) * 1000);
				log.debug("FB auth done (" + token + ":" + expires + ")");
				AuthToken tok = new AuthToken(token, expires);
				req.getSession().setAttribute(AUTH_TOKEN, tok);
				return true;
			} else {
				log.info("FB auth failed, missing access token or expires time");
				return false;
			}
		} catch (HttpException e) {
			throw new IOException("Failed to call Facebook", e);
		} finally {
			method.releaseConnection();
		}
	}

	private Map<String, String> splitValues(String resp) {
		String[] split = resp.split("&");
		Map<String, String> answer = new HashMap<String, String>(2);
		for (String comp : split) {
			String[] values = comp.split("=");
			answer.put(values[0], values[1]);
		}
		return answer;
	}

	private boolean isError(HttpServletRequest req) {
		String code = req.getParameter(ERROR);
		return  (code != null && code.length() > 0);
	}
	
	private boolean isCallback(HttpServletRequest req) {
		String code = req.getParameter(CODE);
		return  (code != null && code.length() > 0);
	}
	
	private void redirect(HttpServletResponse res, String url, boolean breakFrame) throws IOException {
		log.debug("Redirecting to: " + url + "; breaking frame: " + breakFrame);
		// res.sendRedirect(url);
		if(breakFrame) {
			String tmp = "<html><head><script type='text/javascript'>top.location.href=";
			tmp += "\"" + url + "\"";
			tmp += "</script></head></html>";
			res.getWriter().write(tmp);
			res.getWriter().close();
		} else {
			res.sendRedirect(url);
		}
	}
	
	private String getDialogUrl(boolean isFacebook) {
		return DIALOG_URL + "?client_id=" + appId + "&scope=" + appScope + "&redirect_uri=" + (isFacebook ? fbRedirectUri : appRedirectUri);
	}
	
	private String getAuthUrl(String code, boolean isFacebook) {
		return AUTH_URL + "?client_id=" + appId + "&client_secret=" + appSecret + "&code=" + code + "&redirect_uri=" + (isFacebook ?  fbRedirectUri : appRedirectUri);
	}
	
	private boolean hasToken(HttpServletRequest req) {
		HttpSession session = req.getSession();
		AuthToken token = (AuthToken) session.getAttribute(AUTH_TOKEN);
		if(token != null && !token.isExpired()) {
			return true;
		} else {
			session.removeAttribute(AUTH_TOKEN);
			return false;
		}
	}
}
