package net.kalaha.data.entities;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class GameState implements Serializable {

	private static final long serialVersionUID = 5669711273949589971L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="gameId", nullable=false)
	private Game game;
	
	@Column(nullable=false, length=100)
	private String state;

	@Transient
	private int[] realState;
	
	public GameState() { }
	
	public GameState(Game game, int[] realState) {
		setRealState(realState);
		this.game = game;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		String[] arr = state.split(",");
		if(arr.length != 14) throw new IllegalArgumentException("State string should be of length 14 exclusing commas: " + state);
		realState = new int[14];
		for (int i = 0; i < 14; i++) {
			realState[i] = Integer.parseInt(arr[i]);
		}
		this.state = state;
	}
	
	@Transient
	public void setRealState(int[] arr) {
		if(arr.length != 14) throw new IllegalArgumentException("State should be of length 14, was: " + arr.length);
		this.realState = arr;
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			b.append(arr[i]);
			if(i + 1  < arr.length) {
				b.append(",");
			}
		}
		this.state = b.toString();
	}
	
	@Transient
	public int[] getRealState() {
		if(realState == null) {
			setState(state);
		}
		return realState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		result = prime * result + id;
		result = prime * result + Arrays.hashCode(realState);
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		GameState other = (GameState) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		} else if (!game.equals(other.game))
			return false;
		if (id != other.id)
			return false;
		if (!Arrays.equals(realState, other.realState))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GameState [game=" + game + ", id=" + id + ", realState="
				+ Arrays.toString(realState) + ", state=" + state + "]";
	}
}
