package net.kalaha.game.action;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import net.kalaha.json.AbstractAction;

public class End extends AbstractAction {

	private int winnerId;
	private boolean isDraw;
	
	public int getWinnerId() {
		return winnerId;
	}
	
	public void setWinnerId(int winnerId) {
		this.winnerId = winnerId;
	}
	
	public boolean isDraw() {
		return isDraw;
	}
	
	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
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
