package net.kalaha.common.util;

import java.io.UnsupportedEncodingException;

public class Strings {

	public static final String UTF_8 = "UTF-8";

	private Strings() { }

	public static final String fromBytes(byte[] b) {
		try {
			return new String(b, UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
	
	public static final byte[] toBytes(String s) {
		try {
			return s.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
}
