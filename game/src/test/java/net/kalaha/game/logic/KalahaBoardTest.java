package net.kalaha.game.logic;

import junit.framework.TestCase;

public class KalahaBoardTest extends TestCase {

	public void testNewBoardHasEmptyKalahas()
	{
		KalahaBoard kb = new KalahaBoard(6);
		assertEquals(0, kb.getStonesInKalaha(0));
		assertEquals(0, kb.getStonesInKalaha(1));
	}
	
	public void testNewBoardHasSixStonesInEachHole() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			assertEquals(6, kb.getStonesInHole(i, 0));
			assertEquals(6, kb.getStonesInHole(i, 1));
		}
	}
	
	public void testMoveSixKalahas() {
		KalahaBoard kb = new KalahaBoard(6);
		
		kb.moveStones(0, 0);
		
		assertEquals(0, kb.getStonesInHole(0, 0));
		assertEquals(7, kb.getStonesInHole(1, 0));
		assertEquals(7, kb.getStonesInHole(2, 0));
		assertEquals(7, kb.getStonesInHole(3, 0));
		assertEquals(7, kb.getStonesInHole(4, 0));
		assertEquals(7, kb.getStonesInHole(5, 0));
		assertEquals(1, kb.getStonesInKalaha(0));
	}
}
