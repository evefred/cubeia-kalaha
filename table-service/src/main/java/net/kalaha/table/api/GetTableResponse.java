package net.kalaha.table.api;

import java.io.Serializable;

public class GetTableResponse extends TableRequestAction implements Serializable {

	private static final long serialVersionUID = 8570319927151735444L;
	
	private int gameId;
	private int tableId;
	
	public GetTableResponse() { }
	
	public GetTableResponse(int userId, int correlationId, int gameId, int tableId) {
		super(userId, correlationId);
		this.gameId = gameId;
		this.tableId = tableId;
	}

	public GetTableResponse(GetTableRequest q, int tableId) {
		this(q.getUserId(), q.getCorrelationId(), q.getGameId(), tableId);
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
