package net.kalaha.game;

import java.util.List;

import net.kalaha.data.manager.ManagerModule;

import com.cubeia.firebase.guice.game.Configuration;
import com.cubeia.firebase.guice.game.GuiceGame;
import com.google.inject.Module;

public class GameImpl extends GuiceGame {

	@Override
	public Configuration getConfigurationHelp() {
		return new GameGuiceConfig();
	}
	
	@Override
	protected void preInjectorCreation(List<Module> modules) {
		modules.add(new KalahaModule());
		modules.add(new ManagerModule());
	}
}