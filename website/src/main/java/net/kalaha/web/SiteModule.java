package net.kalaha.web;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.Provider;

public class SiteModule extends PropertiesModule {

	@Override
	protected void configure() {
		super.configure();
		bind(WebApplication.class).to(SiteApplication.class);
		bindJpa();
	}
	
	// --- PRIVATE METHODS --- //
	
	private void bindJpa() {
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {
			
			private EntityManagerFactory fact = Persistence.createEntityManagerFactory("kalaha");

			@Override
			public EntityManager get() {
				return fact.createEntityManager();
			}
		});
	}
}
