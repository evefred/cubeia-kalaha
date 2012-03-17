package net.kalaha.table.api;

import java.io.Serializable;

public class CreateGameResponse extends TableRequestAction implements Serializable {

	private static final long serialVersionUID = -3453124953652290479L;
	
	public int opponentId;
	public int gameId;
	public int tableId;
	
	public CreateGameResponse() { }
	
	public CreateGameResponse(int userId, int opponentId, int correlationId, int gameId, int tableId) {
		super(userId, correlationId);
		this.opponentId = opponentId;
		this.gameId = gameId;
		this.tableId = tableId;
	}

	public CreateGameResponse(CreateGameRequest q, int gameId, int tableId) {
		this(q.getUserId(), q.getOpponentId(), q.getCorrelationId(), gameId, tableId);
	}

	public int getOpponentId() {
		return opponentId;
	}

	public void setOpponentId(int opponentId) {
		this.opponentId = opponentId;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
}
