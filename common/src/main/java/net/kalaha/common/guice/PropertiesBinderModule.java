package net.kalaha.common.guice;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public abstract class PropertiesBinderModule extends AbstractModule {
	
	protected Logger log = Logger.getLogger(getClass());

	@Override
	protected void configure() {
		Properties p = loadProperties();
		if(log.isDebugEnabled()) {
			for (Object k : p.keySet()) {
				Object v = p.get(k);
				log.debug("Binding '" + k + "' -> '" + v + "'");
			}
		}
		Names.bindProperties(binder(), p);
	}
	
	protected abstract Properties loadProperties();

}
