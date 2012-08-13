package net.kalaha.game;

import static net.kalaha.common.Errors.ILLEGAL_PLAYER;
import static net.kalaha.common.Errors.OK;
import static net.kalaha.common.Errors.TABLE_CLOSED;
import static net.kalaha.common.TableState.OPEN;
import net.kalaha.game.logic.KalahaBoard;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.game.table.InterceptionResponse;
import com.cubeia.firebase.api.game.table.SeatRequest;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableInterceptor;
import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;

public class TableInterceptorImpl implements TableInterceptor {

	@Inject
	private KalahaBoard board;
	
	/*@Inject
	private UserManager userManager;
	
	@Inject
	private GameManager gameManager;*/
	
	@Log4j
	private Logger log;
	
	@Override
	public InterceptionResponse allowJoin(Table table, SeatRequest req) {
		if(isOpen()) {
			long gameId = board.getGameId();
			int playerId = req.getPlayerId();
			if(playerId == board.getSouthPlayerId() || playerId == board.getNorthPlayerId()) {
				log.debug("Allowing seat for player " + playerId + " at game " + gameId);
				return new InterceptionResponse(true, OK);
			} else {
				log.debug("Denying seat for player " + playerId + " at game " + gameId);
				return new InterceptionResponse(false, ILLEGAL_PLAYER);
			}
		} else {
			log.debug("Denying join, table is closed");
			return new InterceptionResponse(false, TABLE_CLOSED);
		}
	}

	@Override
	public InterceptionResponse allowLeave(Table arg0, int arg1) {
		return new InterceptionResponse(true, OK);
	}

	@Override
	public InterceptionResponse allowReservation(Table arg0, SeatRequest arg1) {
		return new InterceptionResponse(false, -1);
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private boolean isOpen() {
		return board.getTableState().equals(OPEN);
	}
}
