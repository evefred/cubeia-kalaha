package net.kalaha.common.elo;

import static net.kalaha.common.elo.EloCalculator.Result.PLAYER_ONE;
import static net.kalaha.common.elo.EloCalculator.Result.PLAYER_TWO;

import com.google.inject.Inject;

public class DefaultEloCalculator implements EloCalculator {

	@Inject
	private KSelector kSelector;
	
	@Override
	public Matchup calculate(Result res, Matchup match) {
		
		double QA = Math.pow(10, match.playerOne/400);  
	    double QB = Math.pow(10, match.playerTwo/400);  
	  
	    // set the desired k factor  
	    double kA = kSelector.selectK(match.playerOne);  
	    double kB = kSelector.selectK(match.playerTwo);  
		  
	    double EA = QA / (QA + QB);  
	    double EB = QB / (QA + QB);           
	  
	    double SA = 0.5; // draw  
	    double SB = 0.5; // draw  
	    if (res == PLAYER_ONE) {  
	        SA = 1;  
	        SB = 0;  
	    } else if (res == PLAYER_TWO) {  
	        SA = 0;  
	        SB = 1;  
	    }  
	    
	    double s1 = match.playerOne + (kA * (SA - EA));  
	    double s2 = match.playerTwo + (kB * (SB - EB));  
  
		return new Matchup(s1, s2);
	}
}
