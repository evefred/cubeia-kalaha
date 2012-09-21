package net.kalaha.web;

import static net.kalaha.data.entities.RequestStatus.ACCEPTED;
import static net.kalaha.web.SiteApplication.REQUEST_IDS_ATTR;

import java.util.LinkedList;
import java.util.List;

import net.kalaha.data.manager.RequestManager;
import net.kalaha.web.comp.FacebookIndexPanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.protocol.http.WebRequest;

import com.google.inject.Inject;
import com.restfb.FacebookClient;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;

@AuthorizeInstantiation("USER")
public class FacebookIndex extends FacebookBasePage {
	
	@Inject
	private RequestManager requestManager;

	public FacebookIndex(PageParameters p) {
		super(p);
		setup();
	}
	
	private void setup() {
		add(new FacebookIndexPanel("index-panel", gameManager));
		checkRequests();
	}

	private void checkRequests() {
		WebRequest webRequest = (WebRequest)getRequest();
		String[] arr = (String[]) webRequest.getHttpServletRequest().getSession().getAttribute(REQUEST_IDS_ATTR);
		if(arr != null) {
			String userId = getKalahaUser().getExternalId();
			FacebookClient client = getKalahaSession().getClient();
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
	}
}
