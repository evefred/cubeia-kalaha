package net.kalaha.common.util;

import java.lang.reflect.Field;

public class Reflection {

	private Reflection() { }
	
	public static void setPrivateDeclaredField(Object target, String field, Object value) {
		try {
			Class<? extends Object> cl = target.getClass();
			Field f = cl.getDeclaredField(field);
			f.setAccessible(true);
			f.set(target, value);
		} catch(Exception e) {
			throw new IllegalStateException("Failed to set field '" + field + "'", e);
		}
	}
	
	public static Object getPrivateDeclaredField(Object target, String field) {
		try {
			Class<? extends Object> cl = target.getClass();
			while(cl != Object.class) {
				try {
					Field f = cl.getDeclaredField(field);
					f.setAccessible(true);
					return f.get(target);	
				} catch(NoSuchFieldException e) { }
				cl = cl.getSuperclass();
			}
			throw new NoSuchFieldException(field);
		} catch(Exception e) {
			throw new IllegalStateException("Failed to get field '" + field + "'", e);
		}
	}
}
