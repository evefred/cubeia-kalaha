package net.kalaha.game.logic;

public enum KalahaPlayer {
	
	SOUTH(0, KalahaBoard.SOUTH_KALAHA), 
	NORTH(KalahaBoard.SOUTH_KALAHA + 1, KalahaBoard.NORTH_KALAHA);
	
	private int kalahaPit;
	private int firstPit;

	KalahaPlayer(int firstPit, int kalahaPit) {
		this.firstPit = firstPit;
		this.kalahaPit = kalahaPit;
	}

	public int kalaha() {
		return kalahaPit;
	}

	/**
	 * Checks if the current pit is my pit. Kalaha pits are excluded.<br>
	 * <br>
	 * Pits are:<br>
	 * 0-5 => SOUTH<br>
	 * 7-12 => NORTH<br>
	 * 
	 * @param currentPit the id of the pit.
	 * @return true if the given pit is this players pit
	 */
	public boolean isMyPit(int currentPit) {
		return currentPit >= firstPit && currentPit < kalahaPit;
	}

	public boolean isMyKalaha(int currentPit) {
		return currentPit == kalahaPit;
	}

	/**
	 * Translates a global pit number to a player local pit number.
	 * @param currentPit
	 * @return
	 */
	public int toLocalPit(int currentPit) {
		return currentPit - firstPit;
	}

}
