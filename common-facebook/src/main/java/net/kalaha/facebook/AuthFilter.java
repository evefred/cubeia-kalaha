package net.kalaha.facebook;

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
	
	private final Logger log = Logger.getLogger(getClass());
	
	public static final String AUTH_TOKEN = "_authToken";

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
    private String redirectUri;

	@Inject
    @Named("facebook-app-scope")
    private String appScope;
	
	@Inject(optional=true)
    @Named("facebook-allow-anonymous")
    private boolean allowAnonymous = false;
	
	@Inject(optional=true)
    @Named("facebook-force-login-path")
    private String forceLoginPath = "/login/facebook-login";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Injector injector = Guice.createInjector(new AuthModule());
		injector.injectMembers(this);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest) request;
	     HttpServletResponse res = (HttpServletResponse) response;
	     if(hasToken(req)) {
	    	 // Already authorized
	    	 log.trace("User is authenticated, forwarding to Wicket");
	    	 doForward(req, res, chain);
	     } else if(isError(req)) {
	    	 log.info("Authentication denied, reason: " + req.getParameter("error_reason"));
	    	 req.getSession().removeAttribute("_redirect");
	     } else if(isCallback(req)) {
	    	 log.trace("Authentication call back");
	    	 if(doAuthentication(req, res)) {
	    		 log.trace("Authentication sucessful, forwarding to Wicket");
		    	 doForward(req, res, chain);
	    	 } else {
	    		 log.trace("Authentication failed, returning nada");
	    		 req.getSession().removeAttribute("_redirect");
	    	 }
	     } else {
	    	 if(isForceLogin(req) || !allowAnonymous) {
		    	 // Redirect to FB for auth dialog
		    	 log.debug("User is not authenticated, forwarding to FB auth dialog");
		    	 String redir = req.getParameter("redirect");
		    	 if(redir != null) {
		    		 req.getSession().setAttribute("_redirect", redir);
		    	 }
		    	 redirect(res, getDialogUrl());
	    	 } else {
	    		 log.trace("Allowing anonymous access");
	    		 doForward(req, res, chain);
	    	 }
	     }
	}

	

	@Override
	public void destroy() { }
	
	
	// --- PRIVATE METHODS --- //
	
	private void doForward(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		String redir = (String) req.getSession().getAttribute("_redirect");
		req.getSession().removeAttribute("_redirect");
		if(redir == null) {
			redir = req.getParameter("redirect");
		}
		if(redir == null) {
			chain.doFilter(req, res);
		} else {
			redirect(res, redir);
		}
	}
	
	private boolean isForceLogin(HttpServletRequest req) {
		String test = req.getRequestURI();
		return test != null && test.startsWith(forceLoginPath);
	}
	
	private boolean doAuthentication(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String code = req.getParameter(CODE);
		String authUrl = getAuthUrl(code);
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
	
	private void redirect(HttpServletResponse res, String url) throws IOException {
		log.debug("Redirecting to: " + url);
		res.sendRedirect(url);
	}
	
	private String getDialogUrl() {
		return DIALOG_URL + "?client_id=" + appId + "&scope=" + appScope + "&redirect_uri=" + redirectUri;
	}
	
	private String getAuthUrl(String code) {
		return AUTH_URL + "?client_id=" + appId + "&client_secret=" + appSecret + "&code=" + code + "&redirect_uri=" + redirectUri;
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
