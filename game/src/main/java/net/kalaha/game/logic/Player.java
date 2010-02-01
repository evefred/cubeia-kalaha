package net.kalaha.game.logic;

public enum Player {

	SOUTH(0), NORTH(0);
	
	private int id;

	Player(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
