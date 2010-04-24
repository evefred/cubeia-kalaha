package net.kalaha.game;

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
		int gameId = board.getGameId();
		int playerId = req.getPlayerId();
		if(playerId == board.getSouthPlayerId() || playerId == board.getNorthPlayerId()) {
			log.debug("Allowing seat for player " + playerId + " at game " + gameId);
			return new InterceptionResponse(true, 0);
		} else {
			log.debug("Denying seat for player " + playerId + " at game " + gameId);
			return new InterceptionResponse(false, -1);
		}
		/*int seat = req.getSeat();
		if(seat == 0) {
			if(board.getSouthPlayerId() == playerId) {
				return new InterceptionResponse(true, 0);
			} else {
				log.debug("Denying seat for player " + playerId + " at game " + gameId + " position SOUTH");
				return new InterceptionResponse(false, -1);
			}
		} else {
			if(board.getNorthPlayerId() == playerId) {
				return new InterceptionResponse(true, 0);
			} else {
				log.debug("Denying seat for player " + playerId + " at game " + gameId + " position NORTH");
				return new InterceptionResponse(false, -1);
			}
		}*/
	}

	@Override
	public InterceptionResponse allowLeave(Table arg0, int arg1) {
		return new InterceptionResponse(true, 0);
	}

	@Override
	public InterceptionResponse allowReservation(Table arg0, SeatRequest arg1) {
		return new InterceptionResponse(true, 0);
	}
}
