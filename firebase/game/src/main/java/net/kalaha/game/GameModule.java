package net.kalaha.game;

import com.google.inject.AbstractModule;

public class GameModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(OperatorConfig.class).to(DefaultOperatorConfig.class);
	}
}
