package net.kalaha.game.action;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbstractAction {
	
	private String _action;
	
	protected AbstractAction() {
		this._action = getClass().getSimpleName();
	}
	
	public String get_action() {
		return _action;
	}
	
	public void set_action(String name) {
		this._action = name;
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
