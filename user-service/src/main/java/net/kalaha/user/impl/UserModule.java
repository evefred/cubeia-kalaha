package net.kalaha.user.impl;

import net.kalaha.user.api.UserManager;

import com.google.inject.AbstractModule;

public class UserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserManager.class).to(UserManagerImpl.class);
	}
}
