package net.kalaha.table.api;

import java.io.Serializable;

import net.kalaha.json.AbstractAction;

public abstract class TableRequestAction extends AbstractAction implements Serializable {

	private static final long serialVersionUID = 4740437729799629229L;
	
	private int userId;
	private int correlationId;
	
	public TableRequestAction() { }
	
	public TableRequestAction(int userId, int correlationId) {
		this.userId = userId;
		this.correlationId = correlationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(int correlationId) {
		this.correlationId = correlationId;
	}
}
