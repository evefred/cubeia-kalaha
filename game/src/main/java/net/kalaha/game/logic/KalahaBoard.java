package net.kalaha.game.logic;

public class KalahaBoard {

	int[] pits;
	final static int NUMBER_OF_PITS = 6;
	
	public KalahaBoard(int stones) {
		pits = new int[stones * 2 + 2];
		for (int i = 0; i < pits.length; i++) {
			pits[i] = stones;
		}
		setStonesInKalaha(Player.SOUTH, 0);
		setStonesInKalaha(Player.NORTH, 0);
	}

	public int getStonesInKalaha(Player player) {
		if (player == Player.SOUTH) {
			return pits[NUMBER_OF_PITS];
		} else {
			return pits[pits.length - 1];
		}
	}
	
	public void setStonesInKalaha(Player player, int stones) {
		if (player == Player.SOUTH) {
			pits[NUMBER_OF_PITS] = stones;
		} else {
			pits[pits.length - 1] = stones;
		}		
	}

	public int getStonesInHole(int hole, Player player) {
		if (player == Player.SOUTH) {
			return pits[hole];
		} else {
			return pits[NUMBER_OF_PITS + hole + 1];
		}
	}

	public void moveStones(int hole, Player player) {
		int stonesToMove = getStonesInHole(hole, player);
		setStonesInPit(hole, player, 0);
		for (int i = hole + 1; i < hole + stonesToMove + 1; i++) {
			pits[i]++;
		}
	}

	private void setStonesInPit(int hole, Player player, int stones) {
		if (player == Player.SOUTH) {
			pits[hole] = stones;
		} else {
			pits[NUMBER_OF_PITS + hole + 1] = stones;
		}		
	}

}
