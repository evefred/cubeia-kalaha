package net.kalaha.game.impl;

import org.joda.time.Duration;

import net.kalaha.data.manager.GameManager;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ReaperTask implements Runnable {
	
	private static final long DEF_MAX_AGE = Duration.standardDays(30).getMillis();
	
	@Inject
	@SuppressWarnings("unused")
	private GameManager manager;
	
	@Inject(optional=true)
	@Named("service.games.max-age-days")
	@SuppressWarnings("unused")
	private long maxAgeDays = DEF_MAX_AGE;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
