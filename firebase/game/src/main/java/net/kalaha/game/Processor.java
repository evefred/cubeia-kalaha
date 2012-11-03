package net.kalaha.game;

import static net.kalaha.common.TableState.CLOSED;
import net.kalaha.common.json.AbstractAction;
import net.kalaha.common.json.ActionTransformer;
import net.kalaha.data.entities.GameResult;
import net.kalaha.data.manager.GameManager;
import net.kalaha.data.util.TransactionDispatch;
import net.kalaha.game.action.Close;
import net.kalaha.game.action.End;
import net.kalaha.game.action.KalahaAction;
import net.kalaha.game.action.util.ActionUtil;
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
	
	@Inject 
	private GameManager gameManager;
	
	@Inject
	private TransactionDispatch transaction;
	
	public void handle(GameDataAction action, Table table) { 
		Object act = trans.fromUTF8Data(action.getData().array());
		log.debug("Got action: " + act);
		if (act instanceof KalahaAction) {
			handleKalahaAction(action, table, (KalahaAction)act);
		} else {
			log.warn("Unknown action: " + act);
		}
	}

	public void handle(GameObjectAction action, Table table) { 
		String tmp = action.getAttachment().toString();
		AbstractAction act = trans.fromString(tmp);
		handleInternalAction(act, table);
	}


	// --- PRIVATE METHODS --- //
	
	// scheduled or activator action 
	private void handleInternalAction(final AbstractAction act, final Table table) {
		transaction.doInUnitOfWork(new Runnable() {
			
			@Override
			public void run() {
				if(act instanceof Close) {
					tryClose(table);
				} else {
					log.warn("Unknown internal action: " + act);
				}
			}
		});
	}
	
	private void tryClose(Table table) {
		if(haveSeated(table)) {
			log.debug("Ingoring close action as there are players seated; tableId: " + table.getId());
		} else {
			log.debug("Closing table " + table.getId());
			table.getAttributeAccessor().setStringAttribute("state", CLOSED.name());
			board.setTableState(CLOSED);
		}
	}

	private boolean haveSeated(Table table) {
		return table.getPlayerSet().getPlayerCount() > 0;
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

	private void notifyOnAction(GameDataAction action, Table table, KalahaAction act) {
		GameDataAction gda = util.toDataAction(action.getPlayerId(), table.getId(), act);
		table.getNotifier().notifyAllPlayersExceptOne(gda, action.getPlayerId());
	}
	
	private void handleKalahaAction(final GameDataAction action, final Table table, final KalahaAction act) {
		transaction.doInUnitOfWork(new Runnable() {
			
			@Override
			public void run() {
				try {
					doKalahaAction(action, table, act);
				} catch(IllegalMoveException e) { }	
			}
		});
	}

	private void doKalahaAction(GameDataAction action, Table table, KalahaAction act) {
		act.perform(board);
		notifyOnAction(action, table, act);
		boolean end = board.isGameEnded();
		sendStateToAll(action, table);
		long gameId = board.getGameId();
		// Game game = gameManager.getGame(gameId);
		/// board.updateGame(game);
		gameManager.updateGame(gameId, board.getState().getHouses(), board.isOwnersMove());
		if(end) {
			long winnerId = board.getWinningPlayerId();
			gameManager.finishGame(gameId, winnerId, board.isDraw() ? GameResult.DRAW : GameResult.WIN);
			sendEndToAll(action, table);
		}
	}
}
