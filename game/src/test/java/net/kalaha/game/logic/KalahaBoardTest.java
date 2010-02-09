package net.kalaha.game.logic;

import org.testng.annotations.Test; 

import static org.junit.Assert.*;

public class KalahaBoardTest {

	@Test
	public void testNewBoardHasEmptyKalahas()
	{
		KalahaBoard kb = new KalahaBoard(6);
		assertEquals(0, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(Player.NORTH));
	}
	
	@Test
	public void southPlayerCantMoveWhenNoStones() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.SOUTH);
		}
		assertFalse(kb.canPlayerMove(Player.SOUTH));
	}
	
	@Test
	public void northPlayerCantMoveWhenNoStones() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.NORTH);
		}
		assertFalse(kb.canPlayerMove(Player.NORTH));
	}
	
	@Test
	public void northPlayerCanMoveWhenStillStones() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 3; i++) {
			kb.setStonesInPit(0, i, Player.NORTH);
		}
		assertTrue(kb.canPlayerMove(Player.NORTH));
	}
	
	@Test
	public void ownStonesMovedToKalahaOnGameEnd() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(1, i, Player.NORTH);
		}
		kb.endTransfer(Player.NORTH);
		assertEquals(6, kb.getStonesInKalaha(Player.NORTH));
	}
	
	@Test
	public void gameEndsWhenNoMoreStones() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.NORTH);
		}
		kb.setStonesInPit(1, 5, Player.SOUTH);
		kb.moveStones(5, Player.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void testNewBoardHasSixStonesInEachPit() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			assertEquals(6, kb.getStonesInPit(i, Player.SOUTH));
			assertEquals(6, kb.getStonesInPit(i, Player.NORTH));
		}
	}
	
	@Test
	public void testMoveSixKalahas() {
		KalahaBoard kb = new KalahaBoard(6);
		
		kb.moveStones(1, Player.SOUTH);
		assertStones(kb, Player.SOUTH, 6, 0, 7, 7, 7, 7, 1);
		assertEquals(7, kb.getStonesInPit(0, Player.NORTH));
	}

	@Test
	public void testNorthMovesSixKalahas() {
		KalahaBoard kb = new KalahaBoard(6);
		
		kb.moveStones(1, Player.NORTH);
		
		assertStones(kb, Player.NORTH, 6, 0, 7, 7, 7, 7, 1);
		assertEquals(7, kb.getStonesInPit(0, Player.SOUTH));
	}
	
	@Test
	public void testMoveFromSouthToNorth() {
		KalahaBoard kb = new KalahaBoard(6);
		
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, Player.SOUTH));
		assertStones(kb, Player.NORTH, 7, 7, 7, 7, 7);
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
	}
	
	@Test
	public void testSouthSkipsNorthsKalaha() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(10, 5, Player.SOUTH);
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, Player.SOUTH));
		assertStones(kb, Player.NORTH, 7, 7, 7, 7, 7, 7);
		
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(Player.NORTH));
		
		assertStones(kb, Player.SOUTH, 7, 7, 7);
	}
	
	@Test
	public void testNorthSkipsSouthsKalaha() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(20, 5, Player.NORTH);
		
		kb.moveStones(5, Player.NORTH);
		
		assertEquals(0, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(2, kb.getStonesInKalaha(Player.NORTH));
	}
	
	@Test
	public void testLandingInEmptyPitStealsOpponentsStones() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(1, 0, Player.SOUTH);
		kb.setStonesInPit(0, 1, Player.SOUTH);
		
		kb.moveStones(0, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(4, Player.NORTH));
		assertEquals(0, kb.getStonesInPit(1, Player.SOUTH));
		assertEquals(7, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(Player.NORTH));		
	}
	
	@Test
	public void testNorthSteal() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(1, 0, Player.NORTH);
		kb.setStonesInPit(0, 1, Player.NORTH);
		
		kb.moveStones(0, Player.NORTH);

		assertEquals(0, kb.getStonesInPit(4, Player.SOUTH));
		assertEquals(0, kb.getStonesInPit(1, Player.NORTH));		
		assertEquals(7, kb.getStonesInKalaha(Player.NORTH));
		assertEquals(0, kb.getStonesInKalaha(Player.SOUTH));		
	}
	
	@Test
	public void testOnlyStealIfEmptyPitIsMyPit() {
		KalahaBoard kb = new KalahaBoard(6);
		kb.setStonesInPit(3, 5, Player.SOUTH);
		kb.setStonesInPit(0, 1, Player.NORTH);
		
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
	}	
	
	/**
	 * Asserts the stones for a player.
	 * @param kb the board
	 * @param player the player whose stones to check
	 * @param stones  the list of stones, the should be of length 7, last one is kalaha
	 */
	private void assertStones(KalahaBoard kb, Player player, int ... stones) {
		int i = 0;
		for (int pit : stones) {
			assertEquals("Expected " + pit + " stones in pit " + i,
					pit, kb.getStonesInPit(i++, player));
		}
	}	
}
