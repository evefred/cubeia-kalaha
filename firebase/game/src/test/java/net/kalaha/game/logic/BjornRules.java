package net.kalaha.game.logic;

public class BjornRules implements SpecialRules {

	@Override
	public boolean allowStealingFromEmptyPit() {
		return false;
	}
 
	@Override
	public boolean endGameWhenEitherPlayerRunsOutOfStones() {
		return false;
	}

	@Override
	public boolean endGameSweepsBoard() {
		return false;
	}
}
 