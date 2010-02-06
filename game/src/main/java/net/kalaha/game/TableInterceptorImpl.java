package net.kalaha.game;

import com.cubeia.firebase.api.game.table.InterceptionResponse;
import com.cubeia.firebase.api.game.table.SeatRequest;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.game.table.TableInterceptor;

public class TableInterceptorImpl implements TableInterceptor {

	@Override
	public InterceptionResponse allowJoin(Table arg0, SeatRequest arg1) {
		return new InterceptionResponse(true, 0);
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
