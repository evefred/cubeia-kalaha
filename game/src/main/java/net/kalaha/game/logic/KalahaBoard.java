package net.kalaha.game.logic;

public class KalahaBoard {

	int[] pits;
	final static int NUMBER_OF_PITS = 6;
	
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
			return pits[NUMBER_OF_PITS];
		} else {
			return pits[pits.length - 1];
		}
	}
	
	public void setStonesInKalaha(int stones, Player player) {
		if (player == Player.SOUTH) {
			pits[NUMBER_OF_PITS] = stones;
		} else {
			pits[pits.length - 1] = stones;
		}		
	}

	public int getStonesInPit(int pit, Player player) {
		if (player == Player.SOUTH) {
			return pits[pit];
		} else {
			return pits[NUMBER_OF_PITS + pit + 1];
		}
	}

	public void moveStones(int pit, Player player) {
		int stonesToMove = getStonesInPit(pit, player);
		setStonesInPit(0, pit, player);
		for (int i = pit + 1; i < pit + stonesToMove + 1; i++) {
			pits[i]++;
		}
	}

	private void setStonesInPit(int stones, int pit, Player player) {
		if (player == Player.SOUTH) {
			pits[pit] = stones;
		} else {
			pits[NUMBER_OF_PITS + pit + 1] = stones;
		}		
	}

}
