package net.kalaha.game.logic;

import static net.kalaha.entities.GameStatus.ACTIVE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import net.kalaha.entities.Game;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.User;
import net.kalaha.game.IllegalMoveException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class KalahaBoardTest {

	KalahaBoard kb;
	
	@BeforeMethod
	public void setup() {
		kb = new KalahaBoard(6);
	}
	
	@Test 
	public void testNewBoardHasEmptyKalahas()
	{
		assertEquals(kb.getStonesInKalaha(Player.SOUTH), 0);
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 0);
	}
	
	@Test
	public void southPlayerCantMoveWhenNoStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.SOUTH);
		}
		assertFalse(kb.canPlayerMove(Player.SOUTH));
	}
	
	@Test
	public void northPlayerCantMoveWhenNoStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.NORTH);
		}
		assertFalse(kb.canPlayerMove(Player.NORTH));
	}
	
	@Test
	public void northPlayerCanMoveWhenStillStones() {
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
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 6);
	}
	
	@Test
	public void gameEndsWhenOpponentHasNoMoreStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.NORTH);
		}
		// kb.setStonesInPit(1, 5, Player.NORTH);
		kb.setStonesInPit(1, 5, Player.SOUTH);
		kb.moveStones(5, Player.SOUTH);
		assertTrue(kb.isGameEnded());
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 0);
		assertEquals(kb.getStonesInKalaha(Player.SOUTH), 31);
	}
	
	@Test
	public void gameEndsWhenPlayerHasNoMoreStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.SOUTH);
		}
		// kb.setStonesInPit(1, 5, Player.NORTH);
		kb.setStonesInPit(1, 5, Player.SOUTH);
		kb.moveStones(5, Player.SOUTH);
		assertTrue(kb.isGameEnded());
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 36);
		assertEquals(kb.getStonesInKalaha(Player.SOUTH), 1);
	}
	
	@Test
	public void gameEndWithEntity() {
		User u1 = createUser(1, 0);
		User u2 = createUser(2, 0);
		Game g = createGame(u1, u2);
		KalahaBoard kb = new KalahaBoard(g);
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
		assertEquals(kb.getSouthPlayerId(), u1.getId());
		assertEquals(kb.getNorthPlayerId(), u2.getId());
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, Player.NORTH);
		}
		kb.setStonesInPit(1, 5, Player.SOUTH);
		assertFalse(kb.isGameEnded());
		kb.moveStones(5, Player.SOUTH);
		assertTrue(kb.isGameEnded());
		kb.updateGame(g);
		assertEquals(g.getStatus(), GameStatus.FINISHED);
	}


	private Game createGame(User owner, User opponent) {
		Game g = new Game();
		int[] initState = KalahaBoard.getInitState(6);
		g.setStatus(ACTIVE);
		g.updateGameState(initState);
		g.setOpponent(opponent);
		g.setOwner(owner);
		return g;
	}

	@Test
	public void testNewBoardHasSixStonesInEachPit() {
		for (int i = 0; i < 6; i++) {
			assertEquals(6, kb.getStonesInPit(i, Player.SOUTH));
			assertEquals(6, kb.getStonesInPit(i, Player.NORTH));
		}
	}
	
	@Test
	public void testMoveSixKalahas() {
		kb.moveStones(1, Player.SOUTH);
		assertStones(kb, Player.SOUTH, 6, 0, 7, 7, 7, 7, 1);
		assertEquals(7, kb.getStonesInPit(0, Player.NORTH));
	}

	@Test
	public void testNorthMovesSixKalahas() {
		kb.setPlayerToAct(Player.NORTH);
		kb.moveStones(1, Player.NORTH);
		
		assertStones(kb, Player.NORTH, 6, 0, 7, 7, 7, 7, 1);
		assertEquals(7, kb.getStonesInPit(0, Player.SOUTH));
	}
	
	@Test
	public void testMoveFromSouthToNorth() {
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, Player.SOUTH));
		assertStones(kb, Player.NORTH, 7, 7, 7, 7, 7);
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
	}
	
	@Test
	public void testSouthSkipsNorthsKalaha() {
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
		kb.setPlayerToAct(Player.NORTH);
		kb.setStonesInPit(20, 5, Player.NORTH);
		
		kb.moveStones(5, Player.NORTH);
		
		assertEquals(0, kb.getStonesInKalaha(Player.SOUTH));
		assertEquals(2, kb.getStonesInKalaha(Player.NORTH));
	}
	
	@Test
	public void testLandingInEmptyPitStealsOpponentsStones() {
		kb.setStonesInPit(1, 0, Player.SOUTH);
		kb.setStonesInPit(0, 1, Player.SOUTH);
		
		kb.moveStones(0, Player.SOUTH);
		
		assertEquals(kb.getStonesInPit(4, Player.NORTH), 0);
		assertEquals(kb.getStonesInPit(1, Player.SOUTH), 0);
		assertEquals(kb.getStonesInKalaha(Player.SOUTH), 7);
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 0);		
	}
	
	@Test
	public void testNorthSteal() {
		kb.setPlayerToAct(Player.NORTH);
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
		kb.setStonesInPit(3, 5, Player.SOUTH);
		kb.setStonesInPit(0, 1, Player.NORTH);
		
		kb.moveStones(5, Player.SOUTH);
		
		assertEquals(1, kb.getStonesInKalaha(Player.SOUTH));
	}
	
	@Test
	public void testStealWhenOppositePitEmptyAndSpecialRuleOff() {
		KalahaBoard kb = new KalahaBoard(6, Player.NORTH, new BjornRules());
		setupState(kb, 0,10,10,0,1,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(1, Player.NORTH);
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 3);
		assertEquals(kb.getStonesInPit(2, Player.NORTH), 1);
		assertEquals(kb.getStonesInPit(2, Player.SOUTH), 10);
	}	
	
	@Test
	public void testNoNotStealWhenOppositePitEmpty() {
		KalahaBoard kb = new KalahaBoard(6, Player.NORTH);
		setupState(kb, 0,10,0,0,1,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(1, Player.NORTH);
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
		assertEquals(kb.getStonesInKalaha(Player.NORTH), 3);
		assertEquals(kb.getStonesInPit(2, Player.NORTH), 1);
		assertEquals(kb.getStonesInPit(2, Player.SOUTH), 0);
	}	
	
	@Test(expectedExceptions=IllegalMoveException.class)
	public void ignoresActionFromPlayerNotInTurn() {		
		kb.moveStones(0, Player.NORTH);
	}
	
	@Test(expectedExceptions=IllegalMoveException.class)
	public void ignoresActionMovingZeroStones() {
		setupState(kb, 0,10,10,0,1,2,7, 10,1,0,12,5,11,3);
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
		kb.moveStones(0, Player.SOUTH);
	}
	
	@Test
	public void updatesPlayerToActAfterPlayerActs() {
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
		kb.moveStones(1, Player.SOUTH);
		assertEquals(kb.getPlayerToAct(), Player.NORTH);
	}
	
	@Test
	public void playerGetsToActAgainWhenEndingInOwnKalaha() {
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
		kb.setStonesInPit(1, 5, Player.NORTH);
		kb.moveStones(0, Player.SOUTH);
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
	}
	
	@Test
	public void playerDoesNotGetToActAgainAfterSteal() {
		KalahaBoard kb = new KalahaBoard(6, Player.NORTH, new BjornRules());
		setupState(kb, 0,10,10,0,1,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(1, Player.NORTH);
		assertEquals(kb.getPlayerToAct(), Player.SOUTH);
	}
	
	@Test
	public void finishingInKalahaHavingNoMoreStonesEndsGame() {
		setupState(kb, 0,0,0,0,0,1,7, 10,1,0,12,5,11,3);		
		kb.moveStones(5, Player.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void eitherPlayerOutOfStonesDoesNotEndsGameWhenSpecialRuleOn() {
		KalahaBoard kb = new KalahaBoard(6, Player.SOUTH, new BjornRules());
		setupState(kb, 0,0,0,0,0,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(5, Player.SOUTH);
		assertFalse(kb.isGameEnded());
	}
	
	@Test
	public void eitherPlayerOutOfStonesDoesEndGameWhenSpecialRuleOff() {
		setupState(kb, 0,0,0,0,0,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(5, Player.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void endGame() {
		setupState(kb, 1,3,1,1,0,0,30,0,0,0,0,0,0,23);
		kb.moveStones(3, Player.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void endingInKalahaAddsStone() {
		assertEquals(72, sum(6,6,6,6,6,6,0,6,6,6,6,6,6,0));
		kb.moveStones(0, Player.SOUTH);
		assertStones(kb, Player.SOUTH, 0, 7, 7, 7, 7, 7);
		assertEquals(kb.getStonesInKalaha(Player.SOUTH), 1);
	}
	
	// --- PRIVATE METHODS --- //
	
	private int sum(int ... numbers) {
		int result = 0;
		for (int number : numbers) {
			result += number;
		}
		return result;
	}

	private void setupState(KalahaBoard board, int ... pits) {
		for (int i = 0; i < 6; i++) {
			board.setStonesInPit(pits[i], i, Player.SOUTH);
			board.setStonesInPit(pits[i+7], i, Player.NORTH);
		}
		board.setStonesInKalaha(pits[6], Player.SOUTH);
		board.setStonesInKalaha(pits[13], Player.NORTH);
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
		    // assertEquals("Expected " + pit + " stones in pit " + i, pit, kb.getStonesInPit(i++, player));
		    assertEquals(kb.getStonesInPit(i++, player), pit, "Expected " + pit + " stones in pit " + i);
		}
	}	
	
	private User createUser(int id, int operator) {
		User u = new User();
		u.setId(id);
		u.setOperatorId(operator);
		u.setExternalId(String.valueOf(id));
		return u;
	}
}
