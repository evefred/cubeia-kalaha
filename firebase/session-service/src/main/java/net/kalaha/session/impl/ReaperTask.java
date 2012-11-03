package net.kalaha.session.impl;

import java.util.List;

import net.kalaha.data.entities.Session;
import net.kalaha.data.manager.SessionManager;
import net.kalaha.data.util.TransactionDispatch;

import org.apache.log4j.Logger;
import org.joda.time.Duration;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ReaperTask implements Runnable {

	private static final long DEF_MAX_AGE = Duration.standardMinutes(20).getMillis();

	@Inject
	private SessionManager sessions;
	
	@Inject(optional=true)
	@Named("service.sessions.max-age")
	private long maxAge = DEF_MAX_AGE;
	
	@Inject
	private TransactionDispatch trans;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Override
	public void run() {
		trans.doInUnitOfWork(new Runnable() {
			
			@Override
			public void run() {
				log.debug("Running reaper with max age " + maxAge);
				List<Session> reaped = sessions.reapSessions(maxAge);
				log.debug("Reaped " + reaped.size() + " sessions");
				if(log.isTraceEnabled()) {
					for (Session s : reaped) {
						log.trace("Session reaped for user ID: " + s.getUserId());
					}
				}
			}
		});
	}
}