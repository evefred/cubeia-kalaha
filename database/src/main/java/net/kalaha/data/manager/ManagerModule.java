package net.kalaha.data.manager;

import com.google.inject.AbstractModule;

public class ManagerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Transactions.class).to(TransactionsImpl.class);
		bind(UserManager.class).to(UserManagerImpl.class);
		bind(GameManager.class).to(GameManagerImpl.class);
	}
}
