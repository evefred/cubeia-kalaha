package net.kalaha.web.facebook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
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

public class OAuthFilter extends BaseFilter {
	
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
