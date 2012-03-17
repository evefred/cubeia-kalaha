package net.kalaha.facebook;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.wicket.protocol.http.WebApplication;

import com.google.inject.Provider;

public class FacebookModule extends AuthModule {

	@Override
	protected void configure() {
		super.configure();
		bind(WebApplication.class).to(FacebookApplication.class);
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
}
