package net.kalaha.game;

import net.kalaha.common.json.ActionTransformer;
import net.kalaha.game.action.Transformer;

import com.cubeia.firebase.guice.game.EventScoped;
import com.google.inject.AbstractModule;

public class KalahaModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ActionTransformer.class).to(Transformer.class).in(EventScoped.class);
	}
}
