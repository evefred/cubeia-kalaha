package net.kalaha.game;

import net.kalaha.game.action.Sow;
import net.kalaha.game.action.State;
import net.kalaha.game.json.ActionTransformer;
import net.kalaha.game.json.ActionUtil;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.GameDataAction;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.table.Table;
import com.google.inject.Inject;

public class Processor implements GameProcessor {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Inject
	private State state;
	
	@Inject 
	private ActionTransformer trans;
	
	@Inject
	private ActionUtil util;
	
	public void handle(GameDataAction action, Table table) { 
		Object act = trans.fromUTF8Data(action.getData().array());
		log.debug("Got action: " + act + "; State: " + state);
		if(act instanceof Sow) {
			GameDataAction gda = util.toDataAction(action.getPlayerId(), table.getId(), act);
			table.getNotifier().notifyAllPlayersExceptOne(gda, action.getPlayerId());
		} else {
			log.warn("Unknown action: " + act);
		}
	}

	public void handle(GameObjectAction action, Table table) { }

}
