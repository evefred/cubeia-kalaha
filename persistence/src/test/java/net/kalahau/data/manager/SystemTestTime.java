package net.kalahau.data.manager;

import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.DateTime;

import com.google.inject.Singleton;

import net.kalaha.common.util.SystemTime;

@Singleton
public class SystemTestTime implements SystemTime {
	
	public static final AtomicLong TIME = new AtomicLong(0);

	@Override
	public DateTime date() {
		return new DateTime(utc());
	}

	@Override
	public long utc() {
		return TIME.get();
	}
}
