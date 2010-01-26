package net.kalaha.game;

import net.kalaha.game.action.Sow;
import net.kalaha.game.json.ActionTransformer;
import net.kalaha.game.json.ActionUtil;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.GameDataAction;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.table.Table;

public class Processor implements GameProcessor {
	
	private final Logger log = Logger.getLogger(getClass());
	
	public Processor() { }
	
	public void handle(GameDataAction action, Table table) { 
		Object act = ActionTransformer.fromUTF8Data(action.getData().array());
		log.debug("Got action: " + act);
		if(act instanceof Sow) {
			GameDataAction gda = ActionUtil.toDataAction(action.getPlayerId(), table.getId(), act);
			table.getNotifier().notifyAllPlayersExceptOne(gda, action.getPlayerId());
		} else {
			log.warn("Unknown action: " + act);
		}
	}

	public void handle(GameObjectAction action, Table table) { }

}
