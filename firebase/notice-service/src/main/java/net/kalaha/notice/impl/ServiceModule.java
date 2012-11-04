package net.kalaha.notice.impl;

import java.util.concurrent.atomic.AtomicReference;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.name.Names;

public class ServiceModule extends AbstractModule {

	private final AtomicReference<String> accessToken;

	public ServiceModule(AtomicReference<String> accessToken) {
		this.accessToken = accessToken;
	}
	
	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("access-token")).toProvider(new Provider<String>() {
			
			@Override
			public String get() {
				return accessToken.get();
			}
		});
	}
}
