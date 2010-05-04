package net.kalaha.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session implements Serializable {

	private static final long serialVersionUID = -7104410366479459596L;

	@Id
	private String id;
	
	@Column(nullable=false)
	private int userId;
	
	@Column(nullable=false)
	private String externalId;
	
	@Column(nullable=false) 
	private long ttl;
	
	@Column(nullable=false) 
	private long created;
	
	@Column(nullable=false)
	private long lastModified;
	
	@Column(nullable=false)
	private int operatorId;
	
	
	public int getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public long getCreated() {
		return created;
	}
	
	public void setCreated(long created) {
		this.created = created;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	
	public long getTtl() {
		return ttl;
	}
	
	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result
				+ ((externalId == null) ? 0 : externalId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result + operatorId;
		result = prime * result + (int) (ttl ^ (ttl >>> 32));
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (created != other.created)
			return false;
		if (externalId == null) {
			if (other.externalId != null)
				return false;
		} else if (!externalId.equals(other.externalId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastModified != other.lastModified)
			return false;
		if (operatorId != other.operatorId)
			return false;
		if (ttl != other.ttl)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [created=" + created + ", externalId=" + externalId
				+ ", id=" + id + ", lastModified=" + lastModified
				+ ", operatorId=" + operatorId + ", ttl=" + ttl + ", userId="
				+ userId + "]";
	}
}
