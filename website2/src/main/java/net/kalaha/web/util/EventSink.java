package net.kalaha.web.util;

import net.kalaha.data.entities.User;
import net.kalaha.web.action.FacebookUser;

public interface EventSink {

	public void userLoggedIn(User user, FacebookUser fbuser);
	
}
