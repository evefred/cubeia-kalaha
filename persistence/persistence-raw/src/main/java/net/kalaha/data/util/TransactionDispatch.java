package net.kalaha.data.util;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.persist.UnitOfWork;

public class TransactionDispatch {
	
	public static interface Work<T> {
		
		public T execute();
		
	}

	@Inject
	private UnitOfWork work;
	
	private final Logger log = Logger.getLogger(getClass());
	
	public void doInUnitOfWork(final Runnable r) {
		doInUnitOfWork(new Work<Object>() {
			
			@Override
			public Object execute() {
				r.run();
				return null;
			}
		});
	}
	
	public <T> T doInUnitOfWork(Work<T> c) {
		boolean owner = tryEnter();
		try {
			return c.execute();
		} finally {
			if(owner) {
				work.end();
			}
		}
	}

	private boolean tryEnter() {
		try {
			work.begin();
			return true;
		} catch(IllegalArgumentException e) {
			// work already started
			if(log.isDebugEnabled()) {
				log.debug("Nested unit of works detected", e);
			}
			return false;
		}
	}
}
