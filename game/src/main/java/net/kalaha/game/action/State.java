package net.kalaha.game.action;

import java.io.Serializable;

public class State extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -2115183643846951929L;
	
	private int[] pits = new int[14];
	
	public int[] getPits() {
		return pits;
	}
	
	public void setPits(int[] pits) {
		this.pits = pits;
	}
}
