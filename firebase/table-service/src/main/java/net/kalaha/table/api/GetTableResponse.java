package net.kalaha.table.api;

import java.io.Serializable;

public class GetTableResponse extends TableRequestAction implements Serializable {

	private static final long serialVersionUID = 8570319927151735444L;
	
	private long gameId;
	private int tableId;
	
	public GetTableResponse() { }
	
	public GetTableResponse(long userId, int correlationId, long gameId, int tableId) {
		super(userId, correlationId);
		this.gameId = gameId;
		this.tableId = tableId;
	}

	public GetTableResponse(GetTableRequest q, int tableId) {
		this(q.getUserId(), q.getCorrelationId(), q.getGameId(), tableId);
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
}
