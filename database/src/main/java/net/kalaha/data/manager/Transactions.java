package net.kalaha.data.manager;

import javax.persistence.EntityManager;

public interface Transactions {

	public void enter();
	
	public void commit();
	
	public EntityManager current();
	
	public void rollback();
	
	public void exit();

}
