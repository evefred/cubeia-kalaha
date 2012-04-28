package net.kalaha.game.action;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import net.kalaha.json.AbstractAction;

public class State extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -2115183643846951929L;
	
	private int[] houses = new int[14];
	
	private int southPlayerId;
	private int northPlayerId;
	private int playerToAct;
	
	public State(int[] state, int southPlayerId, int northPlayerId, int playerToAct) {
		this.houses = state;
		this.southPlayerId = southPlayerId;
		this.northPlayerId = northPlayerId;
		this.playerToAct = playerToAct;
	}
	
	public State() { }
	
	public int getPlayerToAct() {
		return playerToAct;
	}
	
	public void setPlayerToAct(int playerToAct) {
		this.playerToAct = playerToAct;
	}

	public int[] getHouses() {
		return houses;
	}
	
	public void setHouses(int[] pits) {
		this.houses = pits;
	}

	public int getNorthPlayerId() {
		return northPlayerId;
	}
	
	public void setNorthPlayerId(int northPlayerId) {
		this.northPlayerId = northPlayerId;
	}
	
	public int getSouthPlayerId() {
		return southPlayerId;
	}
	
	public void setSouthPlayerId(int southPlayerId) {
		this.southPlayerId = southPlayerId;
	}
	
	
	// --- COMMONS OBJECT METHODS --- //

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
