package net.kalaha.game.action;

import net.kalaha.json.AbstractAction;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Info extends AbstractAction {

	private Player southPlayer;
	private Player northPlayer;
	
	public Player getNorthPlayer() {
		return northPlayer;
	}
	
	public void setNorthPlayer(Player northPlayer) {
		this.northPlayer = northPlayer;
	}
	
	public void setSouthPlayer(Player southPlayer) {
		this.southPlayer = southPlayer;
	}
	
	public Player getSouthPlayer() {
		return southPlayer;
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
