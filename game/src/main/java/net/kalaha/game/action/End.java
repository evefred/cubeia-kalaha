package net.kalaha.game.action;

public class End extends AbstractAction {

	private int winnerId;
	private boolean isDraw;
	
	public int getWinnerId() {
		return winnerId;
	}
	
	public void setWinnerId(int winnerId) {
		this.winnerId = winnerId;
	}
	
	public boolean isDraw() {
		return isDraw;
	}
	
	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}
}
