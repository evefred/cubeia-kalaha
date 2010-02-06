package net.kalaha.game;

import net.kalaha.game.json.ActionTransformer;
import net.kalaha.game.json.JsonTransformer;

import com.cubeia.firebase.guice.inject.EventScoped;
import com.google.inject.AbstractModule;

public class KalahaModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ActionTransformer.class).to(JsonTransformer.class).in(EventScoped.class);
	}
}
