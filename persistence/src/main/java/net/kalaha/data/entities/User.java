package net.kalaha.data.entities;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1412493585337537363L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=true)
	private String externalId;
	
	@Column(nullable=false)
	private int operatorId;
	
	@Column(nullable=true)
	private String localName;
	
	@Column(nullable=true)
	private String localPassword;
	
	@OneToOne(optional=false, cascade=ALL)
	private UserDetails userDetails;
	
	@OneToOne(optional=false, cascade=ALL)
	private GameStats gameStats;
	
	@Column(nullable=false) 
	private long created;
	
	@Column(nullable=false)
	private long lastModified;
	
	@Column(nullable=false)
	private UserStatus status;
	
	public UserStatus getStatus() {
		return status;
	}
	
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public GameStats getGameStats() {
		return gameStats;
	}
	
	public void setGameStats(GameStats gameStats) {
		this.gameStats = gameStats;
	}
	
	public long getCreated() {
		return created;
	}
	
	public void setCreated(long created) {
		this.created = created;
	}
	
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	
	public long getLastModified() {
		return lastModified;
	}
	
	public UserDetails getUserDetails() {
		return userDetails;
	}
	
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public String getLocalPassword() {
		return localPassword;
	}
	
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	
	public void setLocalPassword(String localPassword) {
		this.localPassword = localPassword;
	}
	
	public int getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getExternalId() {
		return externalId;
	}
	
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result
				+ ((externalId == null) ? 0 : externalId.hashCode());
		result = prime * result
				+ ((gameStats == null) ? 0 : gameStats.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result
				+ ((localName == null) ? 0 : localName.hashCode());
		result = prime * result
				+ ((localPassword == null) ? 0 : localPassword.hashCode());
		result = prime * result + operatorId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((userDetails == null) ? 0 : userDetails.hashCode());
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
		User other = (User) obj;
		if (created != other.created)
			return false;
		if (externalId == null) {
			if (other.externalId != null)
				return false;
		} else if (!externalId.equals(other.externalId))
			return false;
		if (gameStats == null) {
			if (other.gameStats != null)
				return false;
		} else if (!gameStats.equals(other.gameStats))
			return false;
		if (id != other.id)
			return false;
		if (lastModified != other.lastModified)
			return false;
		if (localName == null) {
			if (other.localName != null)
				return false;
		} else if (!localName.equals(other.localName))
			return false;
		if (localPassword == null) {
			if (other.localPassword != null)
				return false;
		} else if (!localPassword.equals(other.localPassword))
			return false;
		if (operatorId != other.operatorId)
			return false;
		if (status != other.status)
			return false;
		if (userDetails == null) {
			if (other.userDetails != null)
				return false;
		} else if (!userDetails.equals(other.userDetails))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", externalId=" + externalId
				+ ", operatorId=" + operatorId + ", localName=" + localName
				+ ", created=" + created + ", lastModified="
				+ lastModified + ", status=" + status + "]";
	}
}
