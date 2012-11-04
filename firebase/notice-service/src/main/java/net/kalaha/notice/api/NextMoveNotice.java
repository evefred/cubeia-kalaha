package net.kalaha.notice.api;

import java.io.Serializable; 

import net.kalaha.common.json.AbstractAction;

public class NextMoveNotice extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -6245160435340018898L;

	private long gameId;
	private long userId; // user to move
	
	public NextMoveNotice() { }
	
	public NextMoveNotice(long gameId, long userId) {
		this.gameId = gameId;
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public long getGameId() {
		return gameId;
	}
	
	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
}
