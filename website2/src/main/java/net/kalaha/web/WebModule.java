package net.kalaha.web;

import net.kalaha.common.guice.ClassPathPropertiesModule;

import com.cubeia.firebase.guice.inject.Log4jTypeListener;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.jpa.JpaPersistModule;

public class WebModule extends ClassPathPropertiesModule {

	public WebModule() {
		super("facebook.properties");
	}
	
	@Override
	protected void configure() {
		super.configure();
		super.install(new JpaPersistModule("kalaha"));
		bindListener(Matchers.any(), new Log4jTypeListener());
	}
}
