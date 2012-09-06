package net.kalaha.web;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.protocol.http.servlet.AbortWithHttpStatusException;

@AuthorizeInstantiation("USER")
public class FacebookRequest extends FacebookBasePage {
	
	public enum Type {
		INVITE,
		CHALLENGE
	}

	public FacebookRequest(PageParameters p) {
		super(p);
		setup();
	}
	
	private void setup() {
		super.setStatelessHint(true);
		String resp = getPageParameters().getString("response");
		if(resp != null) {
			Type type = Type.valueOf(getPageParameters().getString("type"));
			if(type == Type.INVITE) {
				log.info("GOT INVITATION: " + resp);
			} else {
				log.info("GOT CHALLENGE: " + resp);
			}
		}
		throw new AbortWithHttpStatusException(400, false);
	}
}
