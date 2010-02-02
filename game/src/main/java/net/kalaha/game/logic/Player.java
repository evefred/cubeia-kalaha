package net.kalaha.game.logic;

public enum Player {
	
	SOUTH(0, KalahaBoard.SOUTH_KALAHA), NORTH(0, KalahaBoard.NORTH_KALAHA);
	
	private int id;
	private int kalahaPit;

	Player(int id, int kalahaPit) {
		this.id = id;
		this.kalahaPit = kalahaPit;
	}
	
	public int getId() {
		return id;
	}

	public int kalaha() {
		return kalahaPit;
	}

}
