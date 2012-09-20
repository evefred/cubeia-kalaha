package net.kalaha.table.api;

import java.io.Serializable;

import net.kalaha.common.json.AbstractAction;

public abstract class TableRequestAction extends AbstractAction implements Serializable {

	private static final long serialVersionUID = 4740437729799629229L;
	
	private long userId;
	private int correlationId;
	
	public TableRequestAction() { }
	
	public TableRequestAction(long userId, int correlationId) {
		this.userId = userId;
		this.correlationId = correlationId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(int correlationId) {
		this.correlationId = correlationId;
	}
}
