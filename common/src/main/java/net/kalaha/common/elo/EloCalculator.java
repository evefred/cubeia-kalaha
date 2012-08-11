package net.kalaha.common.elo;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultEloCalculator.class)
public interface EloCalculator {
	
	public static class Matchup {
		public final double playerOne;
		public final double playerTwo;
		
		public Matchup(double playerOne, double playerTwo) {
			this.playerOne = playerOne;
			this.playerTwo = playerTwo;
		}
		
		@Override
		public String toString() {
			return playerOne + ":" + playerTwo;
		}
	}
	
	public static enum Result {
		PLAYER_ONE,
		PLAYER_TWO,
		DRAW
	}
	
	public Matchup calculate(Result res, Matchup match);
	
}
