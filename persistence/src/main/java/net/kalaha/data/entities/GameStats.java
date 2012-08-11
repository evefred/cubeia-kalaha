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
	private int id;
	
	private int challengesReceived;
	private int challengesAccepted;
	private int challengesDeclined;
	
	private int sentChallenges;
	private int sentChallengesAccepted;
	private int sentChallengesDeclined;
	
	private int sentInvites;
	private int sentInvitesAccepted;
	
	private int gamesWon;
	private int gamesLost;
	private int gamesDrawn;
	private int gamesTimedOut;
	private int totalGames;
	
	private int friends;
	
	private int eloRating;

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
		return "GameStats [id=" + id + ", challengesReceived="
				+ challengesReceived + ", challengesAccepted="
				+ challengesAccepted + ", challengesDeclined="
				+ challengesDeclined + ", sentChallenges=" + sentChallenges
				+ ", sentChallengesAccepted=" + sentChallengesAccepted
				+ ", sentChallengesDeclined=" + sentChallengesDeclined
				+ ", sentInvites=" + sentInvites + ", sentInvitesAccepted="
				+ sentInvitesAccepted + ", gameWon=" + gamesWon + ", gameLost="
				+ gamesLost + ", gameDrawn=" + gamesDrawn + ", gameTimedOut="
				+ gamesTimedOut + ", totalGames=" + totalGames + ", friends="
				+ friends + ", eloRating=" + eloRating + "]";
	}

	public int getTotalGames() {
		return totalGames;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChallengesReceived() {
		return challengesReceived;
	}

	public void setChallengesReceived(int challengesReceived) {
		this.challengesReceived = challengesReceived;
	}

	public int getChallengesAccepted() {
		return challengesAccepted;
	}

	public void setChallengesAccepted(int challengesAccepted) {
		this.challengesAccepted = challengesAccepted;
	}

	public int getChallengesDeclined() {
		return challengesDeclined;
	}

	public void setChallengesDeclined(int challengesDeclined) {
		this.challengesDeclined = challengesDeclined;
	}

	public int getSentChallenges() {
		return sentChallenges;
	}

	public void setSentChallenges(int sentChallenges) {
		this.sentChallenges = sentChallenges;
	}

	public int getSentChallengesAccepted() {
		return sentChallengesAccepted;
	}

	public void setSentChallengesAccepted(int sentChallengesAccepted) {
		this.sentChallengesAccepted = sentChallengesAccepted;
	}

	public int getSentChallengesDeclined() {
		return sentChallengesDeclined;
	}

	public void setSentChallengesDeclined(int sentChallengesDeclined) {
		this.sentChallengesDeclined = sentChallengesDeclined;
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

	public int getEloRating() {
		return eloRating;
	}

	public void setEloRating(int eloRanking) {
		this.eloRating = eloRanking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + challengesAccepted;
		result = prime * result + challengesDeclined;
		result = prime * result + challengesReceived;
		result = prime * result + eloRating;
		result = prime * result + friends;
		result = prime * result + gamesDrawn;
		result = prime * result + gamesLost;
		result = prime * result + gamesTimedOut;
		result = prime * result + gamesWon;
		result = prime * result + id;
		result = prime * result + sentChallenges;
		result = prime * result + sentChallengesAccepted;
		result = prime * result + sentChallengesDeclined;
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
		if (challengesAccepted != other.challengesAccepted)
			return false;
		if (challengesDeclined != other.challengesDeclined)
			return false;
		if (challengesReceived != other.challengesReceived)
			return false;
		if (eloRating != other.eloRating)
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
		if (sentChallenges != other.sentChallenges)
			return false;
		if (sentChallengesAccepted != other.sentChallengesAccepted)
			return false;
		if (sentChallengesDeclined != other.sentChallengesDeclined)
			return false;
		if (sentInvites != other.sentInvites)
			return false;
		if (sentInvitesAccepted != other.sentInvitesAccepted)
			return false;
		if (totalGames != other.totalGames)
			return false;
		return true;
	}
}
