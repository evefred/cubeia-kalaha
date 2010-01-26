package net.kalaha.game.action;

public class Sow extends AbstractAction {

	private int playerId;
	private int house;
	
	public int getPlayerId() {
		return playerId;
	}
	
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	
	public int getHouse() {
		return house;
	}
	
	public void setHouse(int house) {
		this.house = house;
	}
}
