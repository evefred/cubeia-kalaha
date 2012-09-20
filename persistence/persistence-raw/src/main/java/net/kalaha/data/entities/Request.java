package net.kalaha.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private User inviter;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private User invitee;
	
	@Column(nullable=false) 
	private long created;
	
	@Column(nullable=false)
	private long lastModified;
	
	@Column(nullable=false)
	private RequestStatus status;
	
	@Column
	private RequestType type;
	
	public User getInvitee() {
		return invitee;
	}
	
	public void setInvitee(User invitee) {
		this.invitee = invitee;
	}
	
	public void setType(RequestType type) {
		this.type = type;
	}
	
	public RequestType getType() {
		return type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getInviter() {
		return inviter;
	}

	public void setInviter(User inviter) {
		this.inviter = inviter;
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

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus result) {
		this.status = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((invitee == null) ? 0 : invitee.hashCode());
		result = prime * result + ((inviter == null) ? 0 : inviter.hashCode());
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Request other = (Request) obj;
		if (created != other.created)
			return false;
		if (id != other.id)
			return false;
		if (invitee == null) {
			if (other.invitee != null)
				return false;
		} else if (!invitee.equals(other.invitee))
			return false;
		if (inviter == null) {
			if (other.inviter != null)
				return false;
		} else if (!inviter.equals(other.inviter))
			return false;
		if (lastModified != other.lastModified)
			return false;
		if (status != other.status)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", inviter=" + inviter + ", invitee="
				+ invitee + ", created=" + created + ", lastModified="
				+ lastModified + ", status=" + status + ", type=" + type + "]";
	}
}
