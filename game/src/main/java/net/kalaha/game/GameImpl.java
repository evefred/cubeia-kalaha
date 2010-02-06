package net.kalaha.game;

import com.cubeia.firebase.guice.game.Configuration;
import com.cubeia.firebase.guice.game.GuiceGame;

public class GameImpl extends GuiceGame {

	@Override
	public Configuration getConfigurationHelp() {
		return new GameGuiceConfig();
	}	
}