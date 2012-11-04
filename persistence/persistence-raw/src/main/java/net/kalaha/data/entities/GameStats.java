package net.kalaha.data.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GameStats {

	public static enum Field {
		GAMES_WON("gamesWon"),
		ELO_RANKING("eloRating"),
		TOTAL_GAMES("totalGames"),
		FRIENDS("friends");
		
		private final String fieldName;

		private Field(String fieldName) {
			this.fieldName = fieldName;	
		}
		
		public String getFieldName() {
			return fieldName;
		}
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private int sentInvites;
	private int sentInvitesAccepted;
	
	private int gamesWon;
	private int gamesLost;
	private int gamesDrawn;
	private int gamesTimedOut;
	private int totalGames;
	
	private int friends;
	
	private double eloRating;

	public int getFriends() {
		return friends;
	}
	
	public void setFriends(int friends) {
		this.friends = friends;
	}
	
	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}
	
	@Override
	public String toString() {
		return "GameStats [id=" + id + ", sentInvites=" + sentInvites
				+ ", sentInvitesAccepted=" + sentInvitesAccepted
				+ ", gamesWon=" + gamesWon + ", gamesLost=" + gamesLost
				+ ", gamesDrawn=" + gamesDrawn + ", gamesTimedOut="
				+ gamesTimedOut + ", totalGames=" + totalGames + ", friends="
				+ friends + ", eloRating=" + eloRating + "]";
	}

	public int getTotalGames() {
		return totalGames;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSentInvites() {
		return sentInvites;
	}

	public void setSentInvites(int sentInvites) {
		this.sentInvites = sentInvites;
	}

	public int getSentInvitesAccepted() {
		return sentInvitesAccepted;
	}

	public void setSentInvitesAccepted(int sentInvitesAccepted) {
		this.sentInvitesAccepted = sentInvitesAccepted;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(int gameWon) {
		this.gamesWon = gameWon;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public void setGamesLost(int gameLost) {
		this.gamesLost = gameLost;
	}

	public int getGamesDrawn() {
		return gamesDrawn;
	}

	public void setGamesDrawn(int gameDrawn) {
		this.gamesDrawn = gameDrawn;
	}

	public int getGamesTimedOut() {
		return gamesTimedOut;
	}

	public void setGamesTimedOut(int gameTimedOut) {
		this.gamesTimedOut = gameTimedOut;
	}

	public double getEloRating() {
		return eloRating;
	}

	public void setEloRating(double eloRanking) {
		this.eloRating = eloRanking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(eloRating);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + friends;
		result = prime * result + gamesDrawn;
		result = prime * result + gamesLost;
		result = prime * result + gamesTimedOut;
		result = prime * result + gamesWon;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + sentInvites;
		result = prime * result + sentInvitesAccepted;
		result = prime * result + totalGames;
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
		GameStats other = (GameStats) obj;
		if (Double.doubleToLongBits(eloRating) != Double
				.doubleToLongBits(other.eloRating))
			return false;
		if (friends != other.friends)
			return false;
		if (gamesDrawn != other.gamesDrawn)
			return false;
		if (gamesLost != other.gamesLost)
			return false;
		if (gamesTimedOut != other.gamesTimedOut)
			return false;
		if (gamesWon != other.gamesWon)
			return false;
		if (id != other.id)
			return false;
		if (sentInvites != other.sentInvites)
			return false;
		if (sentInvitesAccepted != other.sentInvitesAccepted)
			return false;
		if (totalGames != other.totalGames)
			return false;
		return true;
	}

	public void incrementTotalGames() {
		totalGames++;
	}

	public void incrementGamesDrawn() {
		gamesDrawn++;
	}

	public void incrementGamesWon() {
		gamesWon++;
	}

	public void incrementGamesLost() {
		gamesLost++;
	}

	public void incrementSentInvites() {
		sentInvites++;
	}

	public void incrementSentInvitesAccepted() {
		sentInvitesAccepted++;
	}
}
