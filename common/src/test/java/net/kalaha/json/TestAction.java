package net.kalaha.json;

import net.kalaha.json.AbstractAction;

public class TestAction extends AbstractAction {

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + house;
		result = prime * result + playerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestAction other = (TestAction) obj;
		if (house != other.house)
			return false;
		if (playerId != other.playerId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "TestAction [playerId=" + playerId + ", house=" + house + "]";
	}
}
