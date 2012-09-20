package net.kalaha.session.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class SessionReaper {

	private static final long DEF_INTERVAL = 5 * 60000; // 5 * 1 min

	@Inject
	private Provider<ReaperTask> tasks;
	
	@Inject(optional=true)
	@Named("service.sessions.reaper-interval")
	private long interval = DEF_INTERVAL;
	
	private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	private ScheduledFuture<?> future;
	
	private final Logger log = Logger.getLogger(getClass());

	public void start() { 
		log.info("Running reaper immediately on start-up");
		tasks.get().run();
		log.info("Scheduling reaper with " + interval + " millis interval");
		future = scheduler.scheduleWithFixedDelay(tasks.get(), interval, interval, MILLISECONDS);
	}
	
	public void stop() { 
		future.cancel(false);
	}
}
