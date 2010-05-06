package net.kalaha.game.action;

import java.io.Serializable;

public class State extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -2115183643846951929L;
	
	private int[] pits = new int[14];
	
	private int southPlayerId;
	
	private int northPlayerId;
	
	public State(int[] state, int southPlayerId, int northPlayerId) {
		this.pits = state;
		this.southPlayerId = southPlayerId;
		this.northPlayerId = northPlayerId;
	}
	
	public State() { }

	public int[] getPits() {
		return pits;
	}
	
	public void setPits(int[] pits) {
		this.pits = pits;
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
}
