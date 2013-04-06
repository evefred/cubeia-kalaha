package net.kalaha.web.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class JacksonJsonFactory implements JsonFactory {

	@Inject
	private ObjectMapper mapper;
	
	@Override
	public <T> T fromJson(String json, Class<T> type) {
		try {
			return mapper.readValue(json, type);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to parse JSON: " + json, e);
		}
	}
	
	@Override
	public <T> String toJson(T value) {
		try {
			return mapper.writeValueAsString(value);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to write JSON", e);
		}
	}
}
