package net.kalaha.game.action;

import java.io.Serializable;

import net.kalaha.common.json.AbstractAction;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class State extends AbstractAction implements Serializable {

	private static final long serialVersionUID = -2115183643846951929L;
	
	private int[] houses = new int[14];
	
	private long southPlayerId;
	private long northPlayerId;
	private long playerToAct;
	
	public State(int[] state, long southPlayerId, long northPlayerId, long playerToAct) {
		this.houses = state;
		this.southPlayerId = southPlayerId;
		this.northPlayerId = northPlayerId;
		this.playerToAct = playerToAct;
	}
	
	public State() { }
	
	public long getPlayerToAct() {
		return playerToAct;
	}
	
	public void setPlayerToAct(long playerToAct) {
		this.playerToAct = playerToAct;
	}

	public int[] getHouses() {
		return houses;
	}
	
	public void setHouses(int[] pits) {
		this.houses = pits;
	}

	public long getNorthPlayerId() {
		return northPlayerId;
	}
	
	public void setNorthPlayerId(long northPlayerId) {
		this.northPlayerId = northPlayerId;
	}
	
	public long getSouthPlayerId() {
		return southPlayerId;
	}
	
	public void setSouthPlayerId(long southPlayerId) {
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
