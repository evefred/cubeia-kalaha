package net.kalaha.user.impl;

import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.service.RoutableService;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RoutableService.class).to(RoutableServiceImpl.class);
		bind(LoginLocator.class).to(LoginLocatorImpl.class);
	}
}
