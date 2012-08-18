package net.kalaha.data.util;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.service.jta.platform.internal.AbstractJtaPlatform;

public class FirebaseJpaPlatform extends AbstractJtaPlatform {

	private static final long serialVersionUID = 8045699568972749316L;
	
	public static final String TM_NAME = "java:comp/env/TransactionManager";
	public static final String UT_NAME = "java:comp/env/UserTransaction";

	@Override
	protected TransactionManager locateTransactionManager() {
		return (TransactionManager) jndiService().locate( TM_NAME );
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		return (UserTransaction) jndiService().locate( UT_NAME );
	}
}
