package net.kalaha.game;

import static net.kalaha.common.TableState.OPEN;
import net.kalaha.data.entities.Game;
import net.kalaha.game.logic.KalahaBoard;

import com.cubeia.firebase.api.game.GameDefinition;
import com.cubeia.firebase.api.game.activator.RequestCreationParticipant;
import com.cubeia.firebase.api.game.lobby.LobbyTableAttributeAccessor;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.lobby.LobbyPath;

public class ActivatorParticipant implements RequestCreationParticipant {

	private final Game game;

	public ActivatorParticipant(Game game) {
		this.game = game;
	}

	@Override
	public void tableCreated(Table table, LobbyTableAttributeAccessor atts) {
		table.getGameState().setState(new KalahaBoard(game));
		atts.setStringAttribute("gameId", String.valueOf(game.getId()));
		atts.setStringAttribute("state", OPEN.name());
	}
	
	@Override
	public String getTableName(GameDefinition arg0, Table table) {
		return "Kalaha [game: " + game.getId() + ", table: " + table.getId() + "]";
	}
	
	@Override
	public LobbyPath getLobbyPathForTable(Table table) {
		return new LobbyPath(table.getMetaData().getGameId(), game.getType().toString().toLowerCase(), table.getId());
	}
	
	@Override
	public boolean reserveSeatsForInvitees() {
		return true;
	}
	
	@Override
	public int[] modifyInvitees(int[] invitees) {
		return invitees;
	}
}