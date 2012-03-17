package net.kalaha.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1412493585337537363L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false)
	private String externalId;
	
	@Column(nullable=false)
	private int operatorId;
	
	@Column(nullable=true)
	private String localName;
	
	@Column(nullable=true)
	private String localPassword;
	
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
		result = prime * result
				+ ((externalId == null) ? 0 : externalId.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((localName == null) ? 0 : localName.hashCode());
		result = prime * result
				+ ((localPassword == null) ? 0 : localPassword.hashCode());
		result = prime * result + operatorId;
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
		if (externalId == null) {
			if (other.externalId != null)
				return false;
		} else if (!externalId.equals(other.externalId))
			return false;
		if (id != other.id)
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
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", externalId=" + externalId
				+ ", operatorId=" + operatorId + ", localName=" + localName
				+ ", localPassword=" + localPassword + "]";
	}
}
