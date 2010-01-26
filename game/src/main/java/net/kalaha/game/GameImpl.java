package net.kalaha.game;

import com.cubeia.firebase.api.game.Game;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.TableListenerProvider;
import com.cubeia.firebase.api.game.context.GameContext;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableListener;

public class GameImpl implements Game, TableListenerProvider {
	
	public void init(GameContext con) { }

	public GameProcessor getGameProcessor() {
		return new Processor();
	}
	
	public void destroy() { }

	
	// --- TABLE LISTENER PROVIDER --- //
	
	@Override
	public TableListener getTableListener(Table table) {
		return new TableListenerImpl();
	}
}
