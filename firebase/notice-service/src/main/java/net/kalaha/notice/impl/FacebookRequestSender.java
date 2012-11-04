package net.kalaha.notice.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchResponse;

public class FacebookRequestSender {

	@Inject
	@Named("access-token")
	private String accessToken;
	
	private final Logger log = Logger.getLogger(getClass());
	
	public void sendRequest(String fbUserId, String msg) {
		FacebookClient client = new DefaultFacebookClient(accessToken);
		BatchRequest req = new BatchRequest.BatchRequestBuilder(fbUserId + "/apprequests")
				.method("POST")
				.parameters(Parameter.with("message", msg))
				.build();
		List<BatchResponse> resps = client.executeBatch(req);
		for (BatchResponse resp : resps) {
			if(resp.getCode() != 200) {
				log.warn("Failed to delete request in batch: " + resp.getBody());
			}
		}
	}
}
