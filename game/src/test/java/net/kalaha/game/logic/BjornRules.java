package net.kalaha.game.logic;

public class BjornRules implements SpecialRules {

	@Override
	public boolean allowStealingFromEmptyPit() {
		return true;
	}
 
	@Override
	public boolean endGameWhenEitherPlayerRunsOutOfStones() {
		return true;
	}

}
 