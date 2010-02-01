package net.kalaha.game.logic;

public class KalahaBoard {

	int[] pits;
	final static int NUMBER_OF_PITS = 6;
	final static int SOUTH_KALAHA = 6;
	final static int NORTH_KALAHA = 13;
	
	public KalahaBoard(int stones) {
		pits = new int[stones * 2 + 2];
		for (int i = 0; i < pits.length; i++) {
			pits[i] = stones;
		}
		setStonesInKalaha(0, Player.SOUTH);
		setStonesInKalaha(0, Player.NORTH);
	}

	public int getStonesInKalaha(Player player) {
		if (player == Player.SOUTH) {
			return pits[SOUTH_KALAHA];
		} else {
			return pits[NORTH_KALAHA];
		}
	}
	
	public void setStonesInKalaha(int stones, Player player) {
		if (player == Player.SOUTH) {
			pits[SOUTH_KALAHA] = stones;
		} else {
			pits[NORTH_KALAHA] = stones;
		}		
	}

	public int getStonesInPit(int pit, Player player) {
		if (player == Player.SOUTH) {
			return pits[pit];
		} else {
			return pits[SOUTH_KALAHA + 1 + pit];
		}
	}

	public void moveStones(int pit, Player player) {
		int stonesToMove = getStonesInPit(pit, player);
		setStonesInPit(0, pit, player);
		int skippedKalahas = 0;
		for (int i = pit + 1; i < pit + stonesToMove + 1; i++) {
			int currentPit = (i + skippedKalahas) % pits.length;
			
			// Refactor this
			if (currentPit == SOUTH_KALAHA && player == Player.NORTH) {
				skippedKalahas++;
				currentPit = (i + skippedKalahas) % pits.length;
			} else if (currentPit == NORTH_KALAHA && player == Player.SOUTH) {
				skippedKalahas++;
				currentPit = (i + skippedKalahas) % pits.length;
			}
			
			pits[currentPit]++;
		}
	}

	public void setStonesInPit(int stones, int pit, Player player) {
		if (player == Player.SOUTH) {
			pits[pit] = stones;
		} else {
			pits[NUMBER_OF_PITS + pit + 1] = stones;
		}		
	}

}
