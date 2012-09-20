package net.kalaha.game.logic;

public class DefaultRules implements SpecialRules {

	@Override
	public boolean allowStealingFromEmptyPit() {
		return true;
	}

	@Override
	public boolean endGameWhenEitherPlayerRunsOutOfStones() {
		return true;
	}
	
	@Override
	public boolean endGameSweepsBoard() {
		return true;
	}
}
