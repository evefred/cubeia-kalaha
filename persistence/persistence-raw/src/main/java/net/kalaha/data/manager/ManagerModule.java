package net.kalaha.data.manager;

import com.google.inject.AbstractModule;

public class ManagerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserManager.class).to(UserManagerImpl.class);
		bind(GameManager.class).to(GameManagerImpl.class);
		bind(SessionManager.class).to(SessionManagerImpl.class);
		bind(RequestManager.class).to(RequestManagerImpl.class);
	}
}
