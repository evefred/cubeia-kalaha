package net.kalaha.table.api;

import java.io.Serializable;

public class CreateGameRequest extends TableRequestAction implements Serializable {

	private static final long serialVersionUID = -5341056980333287152L;
	
	private int opponentId;
	
	public CreateGameRequest() { }
	
	public CreateGameRequest(long userId, int opponentId, int correlationId) {
		super(userId, correlationId);
		this.opponentId = opponentId;
	}
	
	public int getOpponentId() {
		return opponentId;
	}
	
	public void setOpponentId(int opponentId) {
		this.opponentId = opponentId;
	}
}
