package net.kalaha.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {

	private Dates() { }
	
	public static String formatDate(long date) {
		return formatDate(new Date(date));
	}
	
	public static String formatDate(Date date) {
		return new SimpleDateFormat("EEE, d MMM yyyy").format(date);
	}
}
