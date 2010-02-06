package net.kalaha.game;

import net.kalaha.game.action.End;
import net.kalaha.game.action.KalahaAction;
import net.kalaha.game.json.ActionTransformer;
import net.kalaha.game.json.ActionUtil;
import net.kalaha.game.logic.KalahaBoard;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.GameDataAction;
import com.cubeia.firebase.api.action.GameObjectAction;
import com.cubeia.firebase.api.game.GameProcessor;
import com.cubeia.firebase.api.game.table.Table;
import com.google.inject.Inject;

public class Processor implements GameProcessor {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Inject 
	private ActionTransformer trans;
	
	@Inject
	private ActionUtil util;
	
	@Inject
	private KalahaBoard board;
	
	public void handle(GameDataAction action, Table table) { 
		Object act = trans.fromUTF8Data(action.getData().array());
		log.debug("Got action: " + act);
		if(act instanceof KalahaAction) {
			((KalahaAction)act).perform(board);
			notifyOnAction(action, table, act);
			boolean end = board.isGameEnded();
			sendStateToAll(action, table);
			if(end) {
				sendEndToAll(action, table);
			}
		} else {
			log.warn("Unknown action: " + act);
		}
	}

	private void sendStateToAll(GameDataAction action, Table table) {
		GameDataAction gda = util.toDataAction(action.getPlayerId(), table.getId(), board.getState());
		table.getNotifier().notifyAllPlayers(gda);
	}
	
	private void sendEndToAll(GameDataAction action, Table table) {
		End end = new End();
		end.setWinnerId(board.getWinningPlayerId());
		end.setDraw(board.isDraw());
		GameDataAction gda = util.toDataAction(action.getPlayerId(), table.getId(), end);
		table.getNotifier().notifyAllPlayers(gda);
	}

	private void notifyOnAction(GameDataAction action, Table table, Object act) {
		GameDataAction gda = util.toDataAction(action.getPlayerId(), table.getId(), act);
		table.getNotifier().notifyAllPlayersExceptOne(gda, action.getPlayerId());
	}

	public void handle(GameObjectAction action, Table table) { }

}
