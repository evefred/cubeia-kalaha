package net.kalaha.web.dto;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.User;
import net.kalaha.web.util.Dates;

public class WebGame {

	private String id;
	private String opponent;
	private String created;
	private String lastMove;
	private String noMoves;
	private String status;
	private String winner;

	public WebGame(Game game, User current) {
		this.id = String.valueOf(game.getId());
		User opp = game.getMyOpponent(current);
		opponent = opp.getUserDetails().getDisplayName();
		created = Dates.formatDate(game.getCreated());
		lastMove = Dates.formatDate(game.getLastModified());
		noMoves = String.valueOf(game.getStates().size());
		status = game.isMyTurn(opp) ? "Waiting... " : "My Turn!";
		long winId = game.getWinningUser();
		if(winId > 0) {
			this.winner = winId == opp.getId() ? opponent + " Won" : current.getUserDetails().getDisplayName() + " Won";
		} else {
			this.winner = "N/A";
		}
	}
	
	public String getWinner() {
		return winner;
	}
	
	public String getId() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getCreated() {
		return created;
	}
	
	public String getLastMove() {
		return lastMove;
	}
	
	public String getNoMoves() {
		return noMoves;
	}
	
	public String getOpponent() {
		return opponent;
	}
}
