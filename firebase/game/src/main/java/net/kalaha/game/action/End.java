package net.kalaha.game.action;

import net.kalaha.common.json.AbstractAction;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class End extends AbstractAction {

	private long winnerId;
	private boolean isDraw;
	
	public long getWinnerId() {
		return winnerId;
	}
	
	public void setWinnerId(long winnerId) {
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
