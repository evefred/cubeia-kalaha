package net.kalaha.game;

import javax.persistence.EntityManager;

import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.service.persistence.PublicPersistenceService;
import com.cubeia.firebase.guice.inject.FirebaseModule;
import com.google.inject.Provider;

public class ActivatorModule extends FirebaseModule {

	private final PublicPersistenceService serv;
 
	public ActivatorModule(ActivatorContext context) {
		super(context.getServices());
		serv = context.getServices().getServiceInstance(PublicPersistenceService.class);
	}

	@Override
	protected void configure() {
		super.configure();
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {
			
			@Override
			public EntityManager get() {
				return serv.getEntityManager("kalaha");
			}
		});
	}

}
