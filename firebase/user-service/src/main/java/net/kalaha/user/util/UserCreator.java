package net.kalaha.user.util;

import net.kalaha.data.manager.UserManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserCreator implements UserCreatorMBean {
	
	@Inject
	private UserManager manager;
	
	public long createLocalUser(String name, String pass) {
		return manager.createLocalUser(name, pass).getId();
	}
}
