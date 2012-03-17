package net.kalahau.data.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class TestJpaModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {

			private EntityManagerFactory fact = Persistence.createEntityManagerFactory("testUnit");
			
			@Override 
			public EntityManager get() {
				return fact.createEntityManager();
			}
		});
	}
}
