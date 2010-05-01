package net.kalaha.user.impl;

import javax.persistence.EntityManager;

import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.service.RoutableService;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.api.service.persistence.PublicPersistenceService;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.name.Names;

public class ServiceModule extends AbstractModule {

	private final boolean allowTrivialLogin;
	private final ServiceRegistry reg;

	public ServiceModule(ServiceRegistry reg, boolean allowTrivialLogin) {
		this.allowTrivialLogin = allowTrivialLogin; 
		this.reg = reg;
	}

	@Override
	protected void configure() {
		bind(RoutableService.class).to(RoutableServiceImpl.class);
		bind(LoginLocator.class).to(LoginLocatorImpl.class);
		
		// CONFIG
		bindConstant().annotatedWith(Names.named("allow-trivial-login")).to(allowTrivialLogin);
		
		// JPA
		bind(EntityManager.class).toProvider(new Provider<EntityManager>() {

			@Override
			public EntityManager get() {
				PublicPersistenceService serv = reg.getServiceInstance(PublicPersistenceService.class);
				return serv.getEntityManager("kalaha");
			}
		});
	}
}
