package net.kalaha.web;

import net.kalaha.common.guice.ClassPathPropertiesModule;

import org.apache.wicket.protocol.http.WebApplication;

import com.cubeia.firebase.guice.inject.Log4jTypeListener;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.jpa.JpaPersistModule;

public class SiteModule extends ClassPathPropertiesModule {

	public SiteModule() {
		super("facebook.properties");
		super.install(new JpaPersistModule("kalaha"));
	}
	
	@Override
	protected void configure() {
		super.configure();
		bindListener(Matchers.any(), new Log4jTypeListener());
		bind(WebApplication.class).to(SiteApplication.class);
	}
}
