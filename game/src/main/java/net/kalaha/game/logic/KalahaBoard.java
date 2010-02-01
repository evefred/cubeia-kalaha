package net.kalaha.game.logic;

public class KalahaBoard {

	int[] pits;
	final static int NUMBER_OF_PITS = 6;
	
	public KalahaBoard(int stones) {
		pits = new int[stones * 2 + 2];
		for (int i = 0; i < pits.length; i++) {
			pits[i] = stones;
		}
		setStonesInKalaha(0, 0);
		setStonesInKalaha(1, 0);
	}

	public int getStonesInKalaha(int player) {
		if (player == 0) {
			return pits[NUMBER_OF_PITS];
		} else {
			return pits[pits.length - 1];
		}
	}
	
	public void setStonesInKalaha(int player, int stones) {
		if (player == 0) {
			pits[NUMBER_OF_PITS] = stones;
		} else {
			pits[pits.length - 1] = stones;
		}		
	}

	public int getStonesInHole(int hole, int player) {
		if (player == 0) {
			return pits[hole];
		} else {
			return pits[NUMBER_OF_PITS + hole + 1];
		}
	}

	public void moveStones(int hole, int player) {
		int stonesToMove = getStonesInHole(hole, player);
		setStonesInHole(hole, player, 0);
		for (int i = hole + 1; i < hole + stonesToMove + 1; i++) {
			pits[i]++;
		}
	}

	private void setStonesInHole(int hole, int player, int stones) {
		if (player == 0) {
			pits[hole] = stones;
		} else {
			pits[NUMBER_OF_PITS + hole + 1] = stones;
		}		
	}

}
