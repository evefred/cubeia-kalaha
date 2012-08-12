package net.kalaha.game;

import net.kalaha.game.logic.KalahaBoard;

import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.table.TableInterceptor;
import com.cubeia.firebase.api.game.table.TableListener;
import com.cubeia.firebase.guice.game.ConfigurationAdapter;

public class GameGuiceConfig extends ConfigurationAdapter {

	@Override
	public Class<? extends GameProcessor> getGameProcessorClass() {
		return Processor.class;
	}
	
	@Override
	public Class<? extends TableListener> getTableListenerClass() {
		return TableListenerImpl.class;
	}
	
	@Override
	public Class<?> getGameStateClass() {
		return KalahaBoard.class;
	}
	
	@Override
	public Class<? extends TableInterceptor> getTableInterceptorClass() {
		return TableInterceptorImpl.class;
	}
}
