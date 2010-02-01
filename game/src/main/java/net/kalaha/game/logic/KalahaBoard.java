package net.kalaha.game.logic;

public class KalahaBoard {

	int[] holes;
	final int startingStones;
	
	public KalahaBoard(int stones) {
		startingStones = stones;
		holes = new int[stones * 2 + 2];
		for (int i = 0; i < holes.length; i++) {
			holes[i] = stones;
		}
		setStonesInKalaha(0, 0);
		setStonesInKalaha(1, 0);
	}

	public int getStonesInKalaha(int player) {
		if (player == 0) {
			return holes[startingStones];
		} else {
			return holes[holes.length - 1];
		}
	}
	
	public void setStonesInKalaha(int player, int stones) {
		if (player == 0) {
			holes[startingStones] = stones;
		} else {
			holes[holes.length - 1] = stones;
		}		
	}

	public int getStonesInHole(int hole, int player) {
		if (player == 0) {
			return holes[hole];
		} else {
			return holes[startingStones + hole + 1];
		}
	}

	public void moveStones(int hole, int player) {
		int stonesToMove = getStonesInHole(hole, player);
		setStonesInHole(hole, player, 0);
		for (int i = hole + 1; i < hole + stonesToMove + 1; i++) {
			holes[i]++;
		}
	}

	private void setStonesInHole(int hole, int player, int stones) {
		if (player == 0) {
			holes[hole] = stones;
		} else {
			holes[startingStones + hole + 1] = stones;
		}		
	}

}
