package net.kalaha.data.manager;

import com.google.inject.AbstractModule;

public class TransactionalManagerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserManager.class).to(TransactionalUserManager.class);
		bind(GameManager.class).to(TransactionalGameManager.class);
		bind(SessionManager.class).to(TransactionalSessionManager.class);
		bind(RequestManager.class).to(TransactionalRequestManager.class);
	}
}
