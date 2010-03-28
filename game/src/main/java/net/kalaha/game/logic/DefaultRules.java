package net.kalaha.game.logic;

public class DefaultRules implements SpecialRules {

	@Override
	public boolean allowStealingFromEmptyPit() {
		return false;
	}

	@Override
	public boolean endGameWhenEitherPlayerRunsOutOfStones() {
		return false;
	}

}
