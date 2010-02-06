package net.kalaha.game.logic;

import java.io.Serializable;

import net.kalaha.game.action.State;

public class KalahaBoard implements Serializable {

	private static final long serialVersionUID = -3348427879794771338L;

	private final static int NUMBER_OF_PITS = 6;
	
	public final static int SOUTH_KALAHA = 6;
	public final static int NORTH_KALAHA = 13;

	private final State state;
	
	private int southPlayerId;
	private int northPlayerId;

	private boolean gameEnded;
	
	public KalahaBoard(int stones) {
		this.state = new State();
		// pits = new int[stones * 2 + 2];
		for (int i = 0; i < state.getPits().length; i++) {
			state.getPits()[i] = stones;
		}
		setStonesInKalaha(0, Player.SOUTH);
		setStonesInKalaha(0, Player.NORTH);
	}
	
	public State getState() {
		return state;
	}
	
	public int getNorthPlayerId() {
		return northPlayerId;
	}
	
	public int getSouthPlayerId() {
		return southPlayerId;
	}
	
	public void setNorthPlayerId(int northPlayerId) {
		this.northPlayerId = northPlayerId;
	}
	
	public void setSouthPlayerId(int southPlayerId) {
		this.southPlayerId = southPlayerId;
	}
	
	public Player getPlayerForId(int playerId) {
		if(playerId == southPlayerId) {
			return Player.SOUTH;
		} else if(playerId == northPlayerId) {
			return Player.NORTH;
		} else {
			throw new IllegalStateException("No such player: " + playerId);
		}
	}

	public int getStonesInKalaha(Player player) {
		return state.getPits()[player.kalaha()];
	}
	
	public void setStonesInKalaha(int stones, Player player) {
		state.getPits()[player.kalaha()] = stones;
	}

	// Test method
	int getStonesInPit(int pit, Player player) {
		if (player == Player.SOUTH) {
			return state.getPits()[pit];
		} else {
			return state.getPits()[SOUTH_KALAHA + 1 + pit];
		}
	}
	
	public void moveStones(int pit, Player player) {
		int stonesToMove = getStonesInPit(pit, player);
		setStonesInPit(0, pit, player);
		int skippedKalahas = 0;
		int offset = (player == Player.NORTH) ? NUMBER_OF_PITS + 1 : 0;
		for (int i = pit + 1; i < pit + stonesToMove + 1; i++) {
			int currentPit = (i + skippedKalahas + offset) % state.getPits().length;
			
			if (oppentsKalaha(currentPit, player)) {
				skippedKalahas++;
				currentPit = (i + skippedKalahas + offset) % state.getPits().length;
			}
			
			// Steal opponent's stones if last stone lands in empty pit.
			if (!myKalaha(currentPit, player) && i == (pit + stonesToMove) && state.getPits()[currentPit] == 0) {
				int currentStonesInKalaha = getStonesInKalaha(player);
				int stonesInOpponentPit = getStonesInPit(currentPit, getOpponent(player));
				setStonesInKalaha(currentStonesInKalaha + 1 + stonesInOpponentPit, player);
				setStonesInPit(currentPit, 0, getOpponent(player));
			} else {
				state.getPits()[currentPit]++;
			}
		}		
		// Check game end
		if(!canPlayerMove(getOpponent(player))) {
			endTransfer(player);
			gameEnded = true;
		}
	}
	
	// Test method 
	void endTransfer(Player player) {
		// Move remaining stones to kalaha
		for (int i = 0; i < 6; i++) {
			int remaining = getStonesInPit(i, player);
			if(remaining > 0) {
				int tot = getStonesInKalaha(player) + remaining;
				setStonesInKalaha(tot, player);
				setStonesInPit(0, i, player);
			}
		}
	}

	// Test method
	boolean canPlayerMove(Player player) {
		for (int i = 0; i < 6; i++) {
			if(getStonesInPit(i, player) != 0) {
				return true;
			}
		}
		return false;
	}

	private Player getOpponent(Player player) {
		return (player == Player.SOUTH) ? Player.NORTH : Player.SOUTH;
	}

	private boolean oppentsKalaha(int currentPit, Player player) {
		return (currentPit == SOUTH_KALAHA && player == Player.NORTH) || 
			   (currentPit == NORTH_KALAHA && player == Player.SOUTH);		
	}
	
	private boolean myKalaha(int currentPit, Player player) {
		return (currentPit == SOUTH_KALAHA && player == Player.SOUTH) || 
			   (currentPit == NORTH_KALAHA && player == Player.NORTH);		
	}

	// Test method
	void setStonesInPit(int stones, int pit, Player player) {
		if (player == Player.SOUTH) {
			state.getPits()[pit] = stones;
		} else {
			state.getPits()[NUMBER_OF_PITS + pit + 1] = stones;
		}		
	}

	public boolean isGameEnded() {
		return gameEnded;
	}
	
	public boolean isDraw() {
		return getStonesInKalaha(Player.SOUTH) == getStonesInKalaha(Player.NORTH);
	}

	public int getWinningPlayerId() {
		if(getStonesInKalaha(Player.SOUTH) > getStonesInKalaha(Player.NORTH)) {
			return southPlayerId;
		} else if(getStonesInKalaha(Player.SOUTH) < getStonesInKalaha(Player.NORTH)) {
			return northPlayerId;
		} else {
			return -1;
		}
	}
}
