package net.kalaha.data.manager;

import com.google.inject.AbstractModule;

public class ManagerModule extends AbstractModule {

	private final boolean usesJta;

	public ManagerModule() {
		this(false);
	}
	
	
	
	public ManagerModule(boolean usesJta) {
		this.usesJta = usesJta;
	}



	@Override
	protected void configure() {
		if(!usesJta) {
			bind(Transactions.class).to(TransactionsImpl.class);
		} else {
			bind(Transactions.class).to(TransactionsJta.class);
		}
		bind(UserManager.class).to(UserManagerImpl.class);
		bind(GameManager.class).to(GameManagerImpl.class);
	}
}
