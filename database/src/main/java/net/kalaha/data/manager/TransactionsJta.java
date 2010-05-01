package net.kalaha.data.manager;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TransactionsJta implements Transactions {
	
	@Inject
	private Provider<EntityManager> managers;

	@Override
	public void commit() { }

	@Override
	public EntityManager current() {
		return managers.get();
	}

	@Override
	public void enter() { }

	@Override
	public void exit() { }

	@Override
	public void rollback() { }

}
