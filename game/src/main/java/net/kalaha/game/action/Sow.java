package net.kalaha.game.action;

import net.kalaha.game.logic.KalahaBoard;
import net.kalaha.game.logic.Player;

public class Sow extends KalahaAction {

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
		if(house < 0 || house > 5) {
			throw new IllegalActionException("House " + house + " is illegal, must be between 0-5, was: " + house);
		}
		this.house = house;
	}
	
	public void perform(KalahaBoard board) {
		Player p = board.getPlayerForId(playerId);
		board.moveStones(house, p);
	}
}
