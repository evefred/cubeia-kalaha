package net.kalaha.table.api;

import java.io.Serializable;

public class GetTableRequest extends TableRequestAction implements Serializable {

	private static final long serialVersionUID = 2137099430705156440L;
	
	private long gameId;
	
	public GetTableRequest() { }
	
	public GetTableRequest(long userId, int correlationId, long gameId) {
		super(userId, correlationId);
		this.gameId = gameId;
	}
	
	public long getGameId() {
		return gameId;
	}
	
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
}
