package net.kalaha.facebook.page;

import java.util.Collections;
import java.util.List;

import net.kalaha.facebook.BasePage;
import net.kalaha.facebook.FacebookSession;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookXmlRestClient;
import com.google.code.facebookapi.ProfileField;
import com.google.code.facebookapi.schema.User;
import com.google.code.facebookapi.schema.UsersGetInfoResponse;

public class Index extends BasePage {

	public Index(PageParameters parameters) {
		super(parameters);
		setup();
	}
	
	
	// --- PRIVATE METHODS --- //

	private void setup() {
		FacebookSession ses = getFacebookSession();
		FacebookXmlRestClient client = ses.getFacebookClient();
		try {
			long userId = client.users_getLoggedInUser();
			client.users_getInfo(Collections.singleton(userId), Collections.singleton(ProfileField.NAME));
			UsersGetInfoResponse resp = (UsersGetInfoResponse) client.getResponsePOJO();
			List<User> users = resp.getUser();
			add(new Label("name", users.get(0).getName()));
			add(new Label("token", ses.getSession().getId()));
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}
}
