package net.kalaha.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false)
	private GameForm form;
	
	@Column(nullable=false)
	private GameType type;
	
	@Column(nullable=false)
	private GameStatus status;
	
	@Column(nullable=true)
	private GameResult result;
	
	@Column(nullable=false)
	private User owner;
	
	@Column(nullable=true)
	private User opponent;
	
	@Column(nullable=false)
	private long created;
	
	@Column(nullable=false)
	private long lastModified;
	
	@Column(nullable=false)
	private long moveTimeout;
	
	@Column(nullable=false)
	private boolean ownersMove;
	
	@Column(nullable=true)
	private int winningUser;

	public GameResult getResult() {
		return result;
	}
	
	public void setResult(GameResult result) {
		this.result = result;
	}
	
	public GameStatus getStatus() {
		return status;
	}
	
	public void setStatus(GameStatus status) {
		this.status = status;
	}
	
	public void setWinningUser(int userId) {
		this.winningUser = userId;
	}
	
	public int getWinningUser() {
		return winningUser;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameForm getForm() {
		return form;
	}

	public void setForm(GameForm form) {
		this.form = form;
	}

	public GameType getType() {
		return type;
	}

	public void setType(GameType type) {
		this.type = type;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getOpponent() {
		return opponent;
	}

	public void setOpponent(User opponent) {
		this.opponent = opponent;
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

	public long getMoveTimeout() {
		return moveTimeout;
	}

	public void setMoveTimeout(long moveTimeout) {
		this.moveTimeout = moveTimeout;
	}

	public boolean isOwnersMove() {
		return ownersMove;
	}

	public void setOwnersMove(boolean ownersMove) {
		this.ownersMove = ownersMove;
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
