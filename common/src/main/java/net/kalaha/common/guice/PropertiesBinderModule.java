package net.kalaha.common.guice;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public abstract class PropertiesBinderModule extends AbstractModule {
	
	protected Logger log = Logger.getLogger(getClass());

	@Override
	protected void configure() {
		Names.bindProperties(binder(), loadProperties());
	}
	
	protected abstract Properties loadProperties();

}
