package net.kalaha.web;

import static net.kalaha.web.SiteApplication.REQUEST_IDS_ATTR;
import net.kalaha.data.manager.RequestManager;
import net.kalaha.web.comp.FacebookIndexPanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.protocol.http.WebRequest;

import com.google.inject.Inject;

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
			for (String id : arr) {
				log.info("Received incoming request with external ID " + id);
				// TODO requestManager
			}
		}
	}
}
