package net.kalaha.table.api;

import java.io.Serializable;

public class TableQuery implements Serializable {

	private static final long serialVersionUID = -5341056980333287152L;
	
	public final int userId;
	public final int correlationId;
	public final int gameId;
	
	public TableQuery(int userId, int correlationId, int gameId) {
		this.correlationId = correlationId;
		this.gameId = gameId;
		this.userId = userId;
	}
}
