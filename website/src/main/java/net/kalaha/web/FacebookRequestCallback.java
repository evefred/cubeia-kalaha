package net.kalaha.web;

import net.kalaha.data.entities.User;
import net.kalaha.data.manager.RequestManager;
import net.kalaha.web.facebook.FacebookRequest;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.protocol.http.servlet.AbortWithHttpStatusException;

import com.google.inject.Inject;

@AuthorizeInstantiation("USER")
public class FacebookRequestCallback extends FacebookBasePage {
	
	@Inject
	private RequestManager requestManager;
	
	@Inject
	private JsonFactory jsonFactory;
	
	public enum Type {
		INVITE,
		CHALLENGE
	}

	public FacebookRequestCallback(PageParameters p) {
		super(p);
		setup();
	}
	
	private void setup() {
		super.setStatelessHint(true);
		String json = getPageParameters().getString("response");
		if(json != null) {
			User user = getKalahaUser();
			Type type = Type.valueOf(getPageParameters().getString("type"));
			FacebookRequest req = jsonFactory.fromJson(json, FacebookRequest.class);
			if(type == Type.INVITE) {
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
		throw new AbortWithHttpStatusException(400, false);
	}
}
