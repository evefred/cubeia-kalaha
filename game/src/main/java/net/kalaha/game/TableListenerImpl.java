package net.kalaha.game;

import net.kalaha.game.action.util.ActionUtil;
import net.kalaha.game.logic.KalahaBoard;

import com.cubeia.firebase.api.action.GameDataAction;
import com.cubeia.firebase.api.game.player.GenericPlayer;
import com.cubeia.firebase.api.game.player.PlayerStatus;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableListener;
import com.google.inject.Inject;

public class TableListenerImpl implements TableListener {

	@Inject
	private ActionUtil util;	
	
	@Override
	public void playerJoined(Table table, GenericPlayer player) { 
		KalahaBoard s = (KalahaBoard)table.getGameState().getState();
		GameDataAction gda = util.toDataAction(player.getPlayerId(), table.getId(), s.getState());
		table.getNotifier().sendToClient(player.getPlayerId(), gda);
		/*int seatId = player.getSeatId();
		if(seatId == 0) {
			s.setSouthPlayerId(player.getPlayerId());
		} else {
			s.setNorthPlayerId(player.getPlayerId());
		}*/
	}

	@Override
	public void playerLeft(Table arg0, int arg1) { }

	@Override
	public void playerStatusChanged(Table arg0, int arg1, PlayerStatus arg2) { }

	@Override
	public void seatReserved(Table arg0, GenericPlayer arg1) { }

	@Override
	public void watcherJoined(Table arg0, int arg1) { }

	@Override
	public void watcherLeft(Table arg0, int arg1) { }

}
