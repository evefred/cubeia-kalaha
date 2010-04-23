package net.kalaha.facebook;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.apache.wicket.protocol.http.WebApplication;

import com.cubeia.firebase.guice.inject.Log4jTypeListener;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;

public class FacebookModule extends AbstractModule {
	
	private Logger log = Logger.getLogger(getClass());

	@Override
	protected void configure() {
		bindListener(Matchers.any(), new Log4jTypeListener());
		bind(WebApplication.class).to(FacebookApplication.class);
		bindProperties();
		bindJpa();
	}

	private void bindJpa() {
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {
			
			private EntityManagerFactory fact = Persistence.createEntityManagerFactory("kalaha");

			@Override
			public EntityManager get() {
				return fact.createEntityManager();
			}
		});
	}

	private void bindProperties() {
		Names.bindProperties(binder(), loadProperties("facebook.properties"));
	}
	
	protected Properties loadProperties(String path) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(path);
		try {
			if(in == null) throw new FileNotFoundException(path);
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
