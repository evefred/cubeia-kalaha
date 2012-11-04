package net.kalaha.web;

import static net.kalaha.data.entities.RequestStatus.ACCEPTED;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.kalaha.common.util.SafeRunnable;
import net.kalaha.data.entities.Request;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.RequestManager;

import com.google.inject.Inject;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;
import com.restfb.types.AppRequest;

/*
 * NB: Must be placed after auth filter
 */
public class RequestProcessFilter extends BaseGuiceFilter {
	
	@Inject
	private RequestManager requestManager;
	
	private ExecutorService exec = Executors.newCachedThreadPool();

	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		checkRequests((HttpServletRequest) request);
		chain.doFilter(request, response);
	}
	
	
	// --- PRIVATE METHODS --- //

	private void checkRequests(HttpServletRequest request) {
		String[] arr = findRequestIds(request);
		String userId = getExternalUserId(request);
		FacebookClient client = getFacebookClient(request);
		if(arr != null) {
			handleDirectRequests(userId, client, arr);
		}
		if(isFirstAccess(request)) {
			exec.submit(new ClearRequests(userId, client)); // for now we'll clear any outstanding requests
		}
	}

	private boolean isFirstAccess(HttpServletRequest request) {
		return request.getSession().getAttribute(AuthFilter.FIRST_ACC_ATTR) != null;
	}

	/*
	 * Handle all requests ID:s we're given
	 */
	private void handleDirectRequests(String userId, FacebookClient client, String[] arr) {
		List<BatchRequest> requests = new LinkedList<BatchRequest>();
		for (String id : arr) {
			log.info("Received incoming request with external ID " + id);
			requestManager.updateRequestByExternalId(id, ACCEPTED);
			String full_request_id = id + "_" + userId;
			log.debug("Attempting to delete request with full ID: " + full_request_id);
			requests.add(new BatchRequest.BatchRequestBuilder(full_request_id).method("DELETE").build());
		}
		if(requests.size() > 0) {
			log.debug("Executing " + requests.size() + " requests.");
			List<BatchResponse> resps = client.executeBatch(requests.toArray(new BatchRequest[requests.size()]));
			for (BatchResponse resp : resps) {
				if(resp.getCode() != 200) {
					log.warn("Failed to delete request in batch: " + resp.getBody());
				}
			}
		}
	}

	private String[] findRequestIds(HttpServletRequest req) {
		String[] saved = (String[]) req.getSession().getAttribute(AuthFilter.REQUEST_IDS_ATTR);
		if(saved == null) {
			String tmp = req.getParameter("request_ids");
			return tmp == null ? null : tmp.split(",");
		} else {
			req.getSession().removeAttribute(AuthFilter.REQUEST_IDS_ATTR);
			return saved;
		}
	}

	private FacebookClient getFacebookClient(HttpServletRequest request) {
		return (FacebookClient) request.getSession().getAttribute(AuthFilter.CLIENT_ATTR);
	}

	private String getExternalUserId(HttpServletRequest request) {
		return ((User) request.getSession().getAttribute(AuthFilter.USER_ATTR)).getExternalId();
	}
	
	// --- PRIVATE CLASSES --- //
	
	private class ClearRequests extends SafeRunnable {
		
		private String fbUserId;
		private FacebookClient client;
		
		private ClearRequests(String userId, FacebookClient client) {
			fbUserId = userId;
			this.client = client;
		}
		
		protected void execute() {
			Connection<AppRequest> reqs = readAllRequests();
			List<BatchRequest> requests = new LinkedList<BatchRequest>();
			for (AppRequest id : reqs.getData()) {
				String localId = id.getId().substring(0, id.getId().indexOf("_"));
				log.debug("Checking for locally stored request with ID: " + localId);
				Request req = requestManager.getRequestByExternalId(localId);
				if(req == null) {
					log.debug("Attempting to delete request with full ID: " + id.getId());
					requests.add(new BatchRequest.BatchRequestBuilder(id.getId()).method("DELETE").build());
				} else {
					// this is a challenge or invite, do not delete... ?
				}
			}
			if(requests.size() > 0) {
				log.debug("Executing " + requests.size() + " requests.");
				List<BatchResponse> resps = client.executeBatch(requests.toArray(new BatchRequest[requests.size()]));
				for (BatchResponse resp : resps) {
					if(resp.getCode() != 200) {
						log.warn("Failed to delete request in batch: " + resp.getBody());
					}
				}
			}
		}

		private Connection<AppRequest> readAllRequests() {
			return client.fetchConnection(fbUserId + "/apprequests", AppRequest.class);
		}
	}
}
