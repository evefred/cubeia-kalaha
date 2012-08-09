package net.kalaha.common.util;

import org.joda.time.DateTime;

import com.google.inject.Singleton;

@Singleton
public class DefaultSystemTime implements SystemTime {

	@Override
	public DateTime date() {
		return new DateTime();
	}
	
	@Override
	public long utc() {
		return date().getMillis();
	}
}
