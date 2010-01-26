package net.kalaha.game.action;

import java.io.Serializable;
import java.util.Map;

public class State extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -2115183643846951929L;
	
	private int[] seed;
	private Map<Integer, Integer> kalaha;
	
	public int[] getSeed() {
		return seed;
	}
	
	public void setSeed(int[] seed) {
		this.seed = seed;
	}
	
	public Map<Integer, Integer> getKalaha() {
		return kalaha;
	}
	
	public void setKalaha(Map<Integer, Integer> kalaha) {
		this.kalaha = kalaha;
	}
}
