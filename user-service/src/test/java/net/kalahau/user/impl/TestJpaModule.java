package net.kalahau.user.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class TestJpaModule extends AbstractModule {

	@Override
	protected void configure() {
		final EntityManagerFactory fact = Persistence.createEntityManagerFactory("userJpaUnit");
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {

			@Override
			public EntityManager get() {
				return fact.createEntityManager();
			}
		});
	}
}
