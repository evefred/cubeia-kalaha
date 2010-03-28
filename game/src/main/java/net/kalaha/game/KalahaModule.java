package net.kalaha.game;

import javax.persistence.EntityManager;

import net.kalaha.game.json.ActionTransformer;
import net.kalaha.game.json.JsonTransformer;

import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.api.service.transaction.SystemTransactionProvider;
import com.cubeia.firebase.guice.game.EventScoped;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class KalahaModule extends AbstractModule {

	private final ServiceRegistry services;

	public KalahaModule(ServiceRegistry services) {
		this.services = services;
	}

	@Override
	protected void configure() {
		bind(ActionTransformer.class).to(JsonTransformer.class).in(EventScoped.class);
		
		// JPA
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {

			@Override
			public EntityManager get() {
				SystemTransactionProvider serv = services.getServiceInstance(SystemTransactionProvider.class);
				return serv.getEventContext().getEntityManagerForTransaction("kalaha");
			}
		});
	}
}
