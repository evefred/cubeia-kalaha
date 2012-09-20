package net.kalaha.common.guice;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassPathPropertiesModule extends PropertiesBinderModule {

	private final String name;

	public ClassPathPropertiesModule(String name) {
		this.name = name;
	}
	
	protected Properties loadProperties() {
		InputStream in = getClass().getClassLoader().getResourceAsStream(name);
		try {
			if(in == null) throw new FileNotFoundException(name);
			in = new BufferedInputStream(in);
			Properties p = new Properties();
			p.load(in);
			in.close();
			return p;
		} catch(IOException e) {
			log.error("Failed to load config properties: " + e.getMessage(), e);
			return new Properties();
		}
	}
}
