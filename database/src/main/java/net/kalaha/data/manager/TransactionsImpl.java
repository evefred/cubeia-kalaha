package net.kalaha.data.manager;

import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class TransactionsImpl implements Transactions {

	private final ThreadLocal<Handle> transactions = new ThreadLocal<Handle>();
	
	@Inject
	private Provider<EntityManager> managers;
	
	@Log4j
	private Logger log;
	
	@Override
	public void exit() {
		Handle h = get();
		if(h != null) {
			if(h.callstack == 1) {
				if(h.forceCommit) {
					log.trace("Forcing commit on transaction " + h.id);
					commit();
				}
				log.trace("Closing transaction " + h.id);
				if(h.closeCommit) {
					h.transaction.commit();
				}
				h.manager.close();
				set(null);
			} else {
				log.trace("Exiting re-entered transaction " + h.id);
				h.callstack--;
			}
		}
	}
	
	@Override
	public void enter() {
		Handle h = get();
		if(h != null) {
			log.trace("Reentering transaction " + h.id);
			h.callstack++;
		} else {
			h = new Handle(managers.get());
			log.trace("Creating new transaction " + h.id);
			set(h);
		}
	}
	
	@Override
	public EntityManager current() {
		Handle h = get();
		if(h == null) {
			throw new IllegalStateException("No current transaction");
		} else {
			return h.manager;
		}
	}

	@Override
	public void commit() {
		Handle h = get();
		if(h == null) {
			throw new IllegalStateException("No current transaction");
		} else {
			if(h.callstack == 1) {
				if(h.rollbackOnly) {
					log.warn("Transaction " + h.id + " marked as rollback only, aborting commit for rollback");
					h.forceCommit = false;
					h.rollback();
				} else {
					log.trace("Commiting transaction " + h.id);
					h.forceCommit = false;
					h.commit();
				}
			} else {
				log.debug("Marking transaction " + h.id + " as force commit");
				h.forceCommit = true;
			}
		}	
	}

	@Override
	public void rollback() {
		Handle h = get();
		if(h == null) {
			throw new IllegalStateException("No current transaction");
		} else {
			if(h.callstack == 1) {
				log.trace("Rolling back transaction " + h.id);
				h.forceCommit = false;
				h.rollback();
			} else {
				log.debug("Marking transaction " + h.id + " as rollback only");
				h.forceCommit = true;
				h.rollbackOnly = true;
			}
		}
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private Handle get() {
		return transactions.get();
	}
	
	private void set(Handle h) {
		transactions.set(h);
	}

	
	// --- INNER CLASSES --- //
	

	private static class Handle {
		
		private final static AtomicInteger ID_COUNT = new AtomicInteger();
		
		private final int id = ID_COUNT.incrementAndGet();
		
		private EntityManager manager;
		private EntityTransaction transaction;
		private int callstack;
		private boolean rollbackOnly;
		private boolean forceCommit;
		
		private boolean closeCommit = true;
		
		public Handle(EntityManager man) {
			this.callstack = 1;
			transaction = man.getTransaction();
			transaction.begin();
			manager = man;
		}
		
		public void rollback() {
			closeCommit = false;
			transaction.rollback();
		}
		
		public void commit() {
			closeCommit = false;
			transaction.commit();
		}
	}
}
