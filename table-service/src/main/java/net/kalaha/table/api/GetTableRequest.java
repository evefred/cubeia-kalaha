package net.kalaha.table.api;

import java.io.Serializable;

public class GetTableRequest extends TableRequestAction implements Serializable {

	private static final long serialVersionUID = 2137099430705156440L;
	
	private int gameId;
	
	public GetTableRequest() { }
	
	public GetTableRequest(int userId, int correlationId, int gameId) {
		super(userId, correlationId);
		this.gameId = gameId;
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
}
