package net.kalaha.web.auth;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;

public class AuthFilter implements Filter {
	
	public static final String AUTH_TOKEN_ATTR = "_authToken";
	public static final String REQUEST_IDS_ATTR = "_request_ids";

	private static final String HMAC_SHA256 = "HMAC-SHA256";
	private static final String SIGNED_REQUEST = "signed_request";
	
	private final Logger log = Logger.getLogger(getClass());

	private static final String DIALOG_URL = "https://www.facebook.com/dialog/oauth";
	// private static final String AUTH_URL = "https://graph.facebook.com/oauth/access_token";

	private static final String UTF_8 = "UTF-8";
	/*private static final String CODE = "code";
	private static final String ERROR = "error";
	private static final String REDIRECT = "_redirect";*/
	
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
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Injector injector = (Injector) filterConfig.getServletContext().getAttribute(Injector.class.getName());
		injector.injectMembers(this);
	}
	
	
	@Override
	public void destroy() { }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest req = (HttpServletRequest) request;
	     HttpServletResponse res = (HttpServletResponse) response;
	     log.trace("Request URI: " + req.getRequestURI());
	     checkRequests(req);
	     try {
		     if(checkSignedRequest(req, res)) {
		    	 chain.doFilter(request, response);
		     } 
	     } finally {
	    	 removeRequests(req);
	     }
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void removeRequests(HttpServletRequest req) {
		req.getSession().removeAttribute(REQUEST_IDS_ATTR);
	}


	private boolean checkSignedRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String param = request.getParameter(SIGNED_REQUEST);
		if(param != null) {
			log.debug("Signed request: " + param);
			String[] split = param.split("\\.", 2);
			Base64 base64 = new Base64(true);
			byte[] sig = base64.decode(split[0].getBytes(UTF_8));
			JSONObject data = (JSONObject)JSONSerializer.toJSON(new String(base64.decode(split[1].getBytes(UTF_8)), UTF_8));
			log.trace("Signature: " + split[0]);
			log.trace("JSON: " + data);
			if(!data.getString("algorithm").equals(HMAC_SHA256)) {
				log.error("Dropping auth attempt; unknown algorithm: " + data.getString("algorithm"));
				return false;
            }
			byte[] hmac = hmac(split[1], appSecret);
			if(!Arrays.equals(hmac, sig)) {
				log.error("Dropping auth attempt; incorrect sig; expected: " + new String(base64.encode(hmac), UTF_8) + "; was: " + split[0]);
				return false;
            }
            if(!data.has("user_id") || !data.has("oauth_token")) {
            	log.debug("Attempting OAuth");
                String url = getDialogUrl();
                redirect(response, url, true);
                return false;
            } else {
            	//this is authorized user, get their info from Graph API using received access token
                String token = data.getString("oauth_token");
                String exp = data.getString("expires");
                log.debug("Found token: " + token);
                log.trace("Found expiry date: " + exp + " (" + new Date(Long.parseLong(exp) * 1000L) + ")");
                AuthToken tok = new AuthToken(token, Long.parseLong(exp) * 1000L);
				request.getSession(true).setAttribute(AUTH_TOKEN_ATTR, tok);
				return true;
            }
		} else {
			/*if(log.isTraceEnabled()) {
				Map<?, ?> map = request.getParameterMap();
				for(Entry<?, ?> e : map.entrySet()) {
					log.trace("Param: " + e.getKey() + " = " + e.getValue());
				}
			}*/
			if(isFacebookReq(request) && isOathResponse(request)) {
				log.trace("This is a facebook canvas request, but without signed request, redirecting to facebook uri");
				redirect(response, fbFinalredirectUri, false);
				return false;
			} else {
				return true;
			}
		}
	}
	
	private void checkRequests(HttpServletRequest request) {
		String tmp = request.getParameter("request_ids");
		if(tmp != null) {
			String[] arr = tmp.split(",");
			for (String id : arr) {
				log.info("GOT FILTER REQUEST: " + id);
				request.getSession().setAttribute(REQUEST_IDS_ATTR, arr);
			}
		}
	}
	
	private boolean isOathResponse(HttpServletRequest request) {
		String code = request.getParameter("code");
		if(code != null) return true;
		String error = request.getParameter("error");
		if(error != null) return true;
		else return false;
	}


	private String getDialogUrl() throws IOException {
		return DIALOG_URL + "?client_id=" + appId + "&redirect_uri=" + URLEncoder.encode(fbRedirectUri, UTF_8) + "&scope=" + appScope;
	}

	private byte[] hmac(String data, String key) throws IOException {
		SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(UTF_8), "HmacSHA256");
        try {
        	Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
            return mac.doFinal(data.getBytes(UTF_8));
        } catch(Exception e) {
        	throw new IOException(e);
        }
	}	
	
	private void redirect(HttpServletResponse res, String url, boolean breakFrame) throws IOException {
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
	
	private boolean isFacebookReq(HttpServletRequest req) {
		// String test = req.getRequestURI();
		// return test != null && test.startsWith(facebookContext);
		return true;
	}
}
