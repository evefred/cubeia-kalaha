package net.kalaha.web;

import com.google.inject.ImplementedBy;

@ImplementedBy(JacksonJsonFactory.class)
public interface JsonFactory {

	public <T> T fromJson(String json, Class<T> type);
	
	public <T> String toJson(T value);
	
}
