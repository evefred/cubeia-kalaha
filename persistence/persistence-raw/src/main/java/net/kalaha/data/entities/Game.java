package net.kalaha.data.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(nullable=false)
	private GameType type;
	
	@Column(nullable=false)
	private GameStatus status;
	
	@Column(nullable=true)
	private GameResult result;
	
	@ManyToOne
	@JoinColumn(name="ownerId", nullable=false)
	private User owner;
	
	@ManyToOne
	@JoinColumn(name="opponentId", nullable=true)
	private User opponent;
	
	@OneToOne
	@JoinColumn(name="requestId", nullable=true)
	private Request request;
	
	@Column(nullable=false)
	private long created;
	
	@Column(nullable=false)
	private long lastModified;
	
	@Column(nullable=false)
	private long moveTimeout;
	
	@Column(nullable=false)
	private boolean ownersMove = true;
	
	@Column(nullable=true)
	private long winningUser;
	
	@OrderBy("id")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="game", fetch=FetchType.EAGER)
	private List<GameState> states = new LinkedList<GameState>();
	
	public Request getRequest() {
		return request;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public List<GameState> getStates() {
		return states;
	}
	
	public void setStates(List<GameState> states) {
		this.states = states;
	}
	
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
	
	public void setWinningUser(long userId) {
		this.winningUser = userId;
	}
	
	public long getWinningUser() {
		return winningUser;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	@Transient
	public void updateGameState(int[] pits) {
		states.add(new GameState(this, pits));
	}
	
	@Transient
	public GameState getCurrentGameState() {
		return (states.size() == 0 ? null : states.get(states.size() - 1));
	}
	
	@Transient
	public User getMyOpponent(User me) {
		if(owner.equals(me)) {
			return opponent;
		} else {
			return owner;
		}
	}
	
	@Transient 
	public boolean isMyTurn(User me) {
		if(owner.equals(me)) {
			return isOwnersMove();
		} else {
			return !isOwnersMove();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (created ^ (created >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (lastModified ^ (lastModified >>> 32));
		result = prime * result + (int) (moveTimeout ^ (moveTimeout >>> 32));
		result = prime * result
				+ ((opponent == null) ? 0 : opponent.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + (ownersMove ? 1231 : 1237);
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((states == null) ? 0 : states.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (winningUser ^ (winningUser >>> 32));
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
		Game other = (Game) obj;
		if (created != other.created)
			return false;
		if (id != other.id)
			return false;
		if (lastModified != other.lastModified)
			return false;
		if (moveTimeout != other.moveTimeout)
			return false;
		if (opponent == null) {
			if (other.opponent != null)
				return false;
		} else if (!opponent.equals(other.opponent))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (ownersMove != other.ownersMove)
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (result != other.result)
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		if (status != other.status)
			return false;
		if (type != other.type)
			return false;
		if (winningUser != other.winningUser)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", type=" + type + ", status=" + status
				+ ", result=" + result + ", owner=" + owner + ", opponent="
				+ opponent + ", request=" + request + ", created=" + created
				+ ", lastModified=" + lastModified + ", moveTimeout="
				+ moveTimeout + ", ownersMove=" + ownersMove + ", winningUser="
				+ winningUser + ", states=" + states + "]";
	}
}
