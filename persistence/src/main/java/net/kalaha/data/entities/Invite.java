package net.kalaha.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Invite {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private User inviter;
	
	@Column(name="invited", nullable=false)
	private String invitedExtId;
	
	@Column(nullable=false) 
	private long created;
	
	@Column(nullable=false)
	private long lastModified;
	
	@Column(nullable=false)
	private InviteStatus status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getInviter() {
		return inviter;
	}

	public void setInviter(User inviter) {
		this.inviter = inviter;
	}

	public String getInvitedExtId() {
		return invitedExtId;
	}

	public void setInvitedExtId(String invitedExtId) {
		this.invitedExtId = invitedExtId;
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

	public InviteStatus getStatus() {
		return status;
	}

	public void setStatus(InviteStatus result) {
		this.status = result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result + id;
		result = prime * result
				+ ((invitedExtId == null) ? 0 : invitedExtId.hashCode());
		result = prime * result + ((inviter == null) ? 0 : inviter.hashCode());
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result
				+ ((this.status == null) ? 0 : this.status.hashCode());
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
		Invite other = (Invite) obj;
		if (created != other.created)
			return false;
		if (id != other.id)
			return false;
		if (invitedExtId == null) {
			if (other.invitedExtId != null)
				return false;
		} else if (!invitedExtId.equals(other.invitedExtId))
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
		return true;
	}

	@Override
	public String toString() {
		return "Invite [id=" + id + ", inviter=" + inviter + ", invitedExtId="
				+ invitedExtId + ", created=" + created + ", lastModified="
				+ lastModified + ", result=" + status + "]";
	}
}
