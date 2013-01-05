package net.kalaha.game;

import java.util.List;

import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.util.JpaInitializer;

import com.cubeia.firebase.guice.game.Configuration;
import com.cubeia.firebase.guice.game.GuiceGame;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class GameImpl extends GuiceGame {

	@Inject
	@SuppressWarnings("unused")
	private JpaInitializer jpaInit;
	
	@Override
	public Configuration getConfigurationHelp() {
		return new GameGuiceConfig();
	}
	
	@Override
	protected void preInjectorCreation(List<Module> modules) {
		modules.add(new KalahaModule());
		modules.add(new JpaPersistModule("kalaha"));
		modules.add(new ManagerModule());
		modules.add(new GameModule());
	}
	
	@Override
	protected void postInjectorCreation(Injector injector) {
		injector.injectMembers(this);
	}
}