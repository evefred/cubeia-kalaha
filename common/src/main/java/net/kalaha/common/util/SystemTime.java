package net.kalaha.common.util;

import org.joda.time.DateTime;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultSystemTime.class)
public interface SystemTime {

	public DateTime date();
	
	public long utc();
	
}
