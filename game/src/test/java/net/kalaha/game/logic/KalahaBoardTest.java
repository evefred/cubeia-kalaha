package net.kalaha.game.logic;

import junit.framework.TestCase;

public class KalahaBoardTest extends TestCase {

	public void testNewBoardHasEmptyKalahas()
	{
		KalahaBoard kb = new KalahaBoard(6);
		assertEquals(0, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(Player.NORTH));
	}
	
	public void testNewBoardHasSixStonesInEachPit() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			assertEquals(6, kb.getStonesInPit(i, Player.SOUTH));
			assertEquals(6, kb.getStonesInPit(i, Player.NORTH));
		}
	}
	
	public void testMoveSixKalahas() {
		KalahaBoard kb = new KalahaBoard(6);
		
		kb.moveStones(0, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(0, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(1, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(2, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(3, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(4, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(5, Player.SOUTH));
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
	}
}
