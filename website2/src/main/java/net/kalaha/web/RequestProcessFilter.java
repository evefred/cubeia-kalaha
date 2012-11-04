package net.kalaha.web;

import static net.kalaha.data.entities.RequestStatus.ACCEPTED;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.kalaha.data.entities.User;
import net.kalaha.data.manager.RequestManager;

import com.google.inject.Inject;
import com.restfb.FacebookClient;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;

/*
 * NB: Must be placed after auth filter
 */
public class RequestProcessFilter extends BaseGuiceFilter {
	
	@Inject
	private RequestManager requestManager;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		checkRequests((HttpServletRequest) request);
		chain.doFilter(request, response);
	}

	private void checkRequests(HttpServletRequest request) {
		String[] arr = findRequestIds(request);
		if(arr == null) {
			return; // SANITY CHECK
		}
		String userId = getExternalUserId(request);
		FacebookClient client = getFacebookClient(request);
		List<BatchRequest> requests = new LinkedList<BatchRequest>();
		for (String id : arr) {
			log.info("Received incoming request with external ID " + id);
			requestManager.updateRequestByExternalId(id, ACCEPTED);
			String full_request_id = id + "_" + userId;
			log.debug("Attempting to delete request with full ID: " + full_request_id);
			requests.add(new BatchRequest.BatchRequestBuilder(full_request_id).method("DELETE").build());
		}
		log.debug("Executing " + requests.size() + " requests.");
		List<BatchResponse> resps = client.executeBatch(requests.toArray(new BatchRequest[requests.size()]));
		for (BatchResponse resp : resps) {
			if(resp.getCode() != 200) {
				log.warn("Failed to delete request in batch: " + resp.getBody());
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
}
