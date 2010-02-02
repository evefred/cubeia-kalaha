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
	
	public void testMoveFromSouthToNorth() {
		KalahaBoard kb = new KalahaBoard(6);
		
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(0, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(1, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(2, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(3, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(4, Player.NORTH));
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
	}
	
	public void testSouthSkipsNorthsKalaha() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(10, 5, Player.SOUTH);
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(0, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(1, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(2, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(3, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(4, Player.NORTH));
		assertEquals(7, kb.getStonesInPit(5, Player.NORTH));
		
		
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(Player.NORTH));
		
		assertEquals(7, kb.getStonesInPit(0, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(1, Player.SOUTH));
		assertEquals(7, kb.getStonesInPit(2, Player.SOUTH));		
	}
	
	public void testNorthSkipsSouthsKalaha() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(20, 5, Player.NORTH);
		
		kb.moveStones(5, Player.NORTH);
		
		assertEquals(0, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(2, kb.getStonesInKalaha(Player.NORTH));
	}
	
	public void testLandingInEmptyPitStealsOpponentsStones() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(1, 0, Player.SOUTH);
		kb.setStonesInPit(0, 1, Player.SOUTH);
		
		kb.moveStones(0, Player.SOUTH);
		
		assertEquals(7, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(Player.NORTH));		
	}
	
}
