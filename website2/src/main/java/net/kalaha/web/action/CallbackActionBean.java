package net.kalaha.web.action;

import net.kalaha.data.entities.RequestType;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.RequestManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.web.util.JsonFactory;
import net.kalaha.web.util.NullResolution;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.inject.Inject;

@UrlBinding("/Callback.action")
public class CallbackActionBean extends BaseActionBean {

	@Inject
	private RequestManager requestManager;
	
	@Inject
	private UserManager userManager;
	
	@Inject
	private JsonFactory jsonFactory;
	
	@Inject
	private String requestData;
	
	@Inject 
	private String type;
	
	@DefaultHandler
    public Resolution view() {
		if(requestData != null) {
			User user = getCurrentUser();
			log.debug("Request from user " + user.getId() + "; json: " + requestData);
			RequestType type = RequestType.valueOf(this.type);
			FacebookRequest req = jsonFactory.fromJson(requestData, FacebookRequest.class);
			int operatorId = getContext().getOperatorId();
			if(type == RequestType.INVITATION) {
				for (String to : req.getTo()) {
					log.debug("User " + user.getId() + " invites external ID " + to);
					requestManager.invite(user, to, operatorId, req.getRequest());
				}
			} else {
				for (String to : req.getTo()) {
					User receiver = userManager.getUserByExternalId(to, operatorId);
					log.debug("User " + user.getId() + " challenges user " + receiver);
					requestManager.challenge(user, receiver, req.getRequest());
				}
			}
		} else {
			log.warn("Received request without a JSON object!");
		}
		return new NullResolution();
	}
	
	public String getRequestData() {
		return requestData;
	}
	
	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
