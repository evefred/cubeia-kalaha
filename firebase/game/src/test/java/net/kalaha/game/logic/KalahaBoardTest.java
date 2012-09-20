package net.kalaha.game.logic;

import static net.kalaha.data.entities.GameStatus.ACTIVE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.User;
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
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.SOUTH), 0);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 0);
	}
	
	@Test
	public void southPlayerCantMoveWhenNoStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, KalahaPlayer.SOUTH);
		}
		assertFalse(kb.canPlayerMove(KalahaPlayer.SOUTH));
	}
	
	@Test
	public void northPlayerCantMoveWhenNoStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, KalahaPlayer.NORTH);
		}
		assertFalse(kb.canPlayerMove(KalahaPlayer.NORTH));
	}
	
	@Test
	public void northPlayerCanMoveWhenStillStones() {
		for (int i = 0; i < 3; i++) {
			kb.setStonesInPit(0, i, KalahaPlayer.NORTH);
		}
		assertTrue(kb.canPlayerMove(KalahaPlayer.NORTH));
	}
	
	@Test
	public void ownStonesMovedToKalahaOnGameEnd() {
		KalahaBoard kb = new KalahaBoard(6);
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(1, i, KalahaPlayer.NORTH);
		}
		kb.endTransfer(KalahaPlayer.NORTH);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 6);
	}
	
	@Test
	public void gameEndsWhenOpponentHasNoMoreStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, KalahaPlayer.NORTH);
		}
		// kb.setStonesInPit(1, 5, Player.NORTH);
		kb.setStonesInPit(1, 5, KalahaPlayer.SOUTH);
		kb.moveStones(5, KalahaPlayer.SOUTH);
		assertTrue(kb.isGameEnded());
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 0);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.SOUTH), 31);
	}
	
	@Test
	public void gameEndsWhenPlayerHasNoMoreStones() {
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, KalahaPlayer.SOUTH);
		}
		// kb.setStonesInPit(1, 5, Player.NORTH);
		kb.setStonesInPit(1, 5, KalahaPlayer.SOUTH);
		kb.moveStones(5, KalahaPlayer.SOUTH);
		assertTrue(kb.isGameEnded());
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 36);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.SOUTH), 1);
	}
	
	@Test
	public void gameEndWithEntity() {
		User u1 = createUser(1, 0);
		User u2 = createUser(2, 0);
		Game g = createGame(u1, u2);
		KalahaBoard kb = new KalahaBoard(g);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
		assertEquals(kb.getSouthPlayerId(), u1.getId());
		assertEquals(kb.getNorthPlayerId(), u2.getId());
		for (int i = 0; i < 6; i++) {
			kb.setStonesInPit(0, i, KalahaPlayer.NORTH);
		}
		kb.setStonesInPit(1, 5, KalahaPlayer.SOUTH);
		assertFalse(kb.isGameEnded());
		kb.moveStones(5, KalahaPlayer.SOUTH);
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
			assertEquals(6, kb.getStonesInPit(i, KalahaPlayer.SOUTH));
			assertEquals(6, kb.getStonesInPit(i, KalahaPlayer.NORTH));
		}
	}
	
	@Test
	public void testMoveSixKalahas() {
		kb.moveStones(1, KalahaPlayer.SOUTH);
		assertStones(kb, KalahaPlayer.SOUTH, 6, 0, 7, 7, 7, 7, 1);
		assertEquals(7, kb.getStonesInPit(0, KalahaPlayer.NORTH));
	}

	@Test
	public void testNorthMovesSixKalahas() {
		kb.setPlayerToAct(KalahaPlayer.NORTH);
		kb.moveStones(1, KalahaPlayer.NORTH);
		
		assertStones(kb, KalahaPlayer.NORTH, 6, 0, 7, 7, 7, 7, 1);
		assertEquals(7, kb.getStonesInPit(0, KalahaPlayer.SOUTH));
	}
	
	@Test
	public void testMoveFromSouthToNorth() {
		kb.moveStones(5, KalahaPlayer.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, KalahaPlayer.SOUTH));
		assertStones(kb, KalahaPlayer.NORTH, 7, 7, 7, 7, 7);
		assertEquals(1, kb.getStonesInKalaha(KalahaPlayer.SOUTH));
	}
	
	@Test
	public void testSouthSkipsNorthsKalaha() {
		kb.setStonesInPit(10, 5, KalahaPlayer.SOUTH);
		kb.moveStones(5, KalahaPlayer.SOUTH);
		
		assertEquals(0, kb.getStonesInPit(5, KalahaPlayer.SOUTH));
		assertStones(kb, KalahaPlayer.NORTH, 7, 7, 7, 7, 7, 7);
		
		assertEquals(1, kb.getStonesInKalaha(KalahaPlayer.SOUTH));
		assertEquals(0, kb.getStonesInKalaha(KalahaPlayer.NORTH));
		
		assertStones(kb, KalahaPlayer.SOUTH, 7, 7, 7);
	}
	
	@Test
	public void testNorthSkipsSouthsKalaha() {
		kb.setPlayerToAct(KalahaPlayer.NORTH);
		kb.setStonesInPit(20, 5, KalahaPlayer.NORTH);
		
		kb.moveStones(5, KalahaPlayer.NORTH);
		
		assertEquals(0, kb.getStonesInKalaha(KalahaPlayer.SOUTH));
		assertEquals(2, kb.getStonesInKalaha(KalahaPlayer.NORTH));
	}
	
	@Test
	public void testLandingInEmptyPitStealsOpponentsStones() {
		kb.setStonesInPit(1, 0, KalahaPlayer.SOUTH);
		kb.setStonesInPit(0, 1, KalahaPlayer.SOUTH);
		
		kb.moveStones(0, KalahaPlayer.SOUTH);
		
		assertEquals(kb.getStonesInPit(4, KalahaPlayer.NORTH), 0);
		assertEquals(kb.getStonesInPit(1, KalahaPlayer.SOUTH), 0);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.SOUTH), 7);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 0);		
	}
	
	@Test
	public void testNorthSteal() {
		kb.setPlayerToAct(KalahaPlayer.NORTH);
		kb.setStonesInPit(1, 0, KalahaPlayer.NORTH);
		kb.setStonesInPit(0, 1, KalahaPlayer.NORTH);
		
		kb.moveStones(0, KalahaPlayer.NORTH);

		assertEquals(0, kb.getStonesInPit(4, KalahaPlayer.SOUTH));
		assertEquals(0, kb.getStonesInPit(1, KalahaPlayer.NORTH));		
		assertEquals(7, kb.getStonesInKalaha(KalahaPlayer.NORTH));
		assertEquals(0, kb.getStonesInKalaha(KalahaPlayer.SOUTH));		
	}
	
	@Test
	public void testOnlyStealIfEmptyPitIsMyPit() {
		kb.setStonesInPit(3, 5, KalahaPlayer.SOUTH);
		kb.setStonesInPit(0, 1, KalahaPlayer.NORTH);
		
		kb.moveStones(5, KalahaPlayer.SOUTH);
		
		assertEquals(1, kb.getStonesInKalaha(KalahaPlayer.SOUTH));
	}
	
	@Test
	public void testStealWhenOppositePitEmptyAndSpecialRuleOff() {
		KalahaBoard kb = new KalahaBoard(6, KalahaPlayer.NORTH, new BjornRules());
		setupState(kb, 0,10,10,0,1,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(1, KalahaPlayer.NORTH);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 3);
		assertEquals(kb.getStonesInPit(2, KalahaPlayer.NORTH), 1);
		assertEquals(kb.getStonesInPit(2, KalahaPlayer.SOUTH), 10);
	}	
	
	@Test
	public void testNoNotStealWhenOppositePitEmpty() {
		KalahaBoard kb = new KalahaBoard(6, KalahaPlayer.NORTH);
		setupState(kb, 0,10,0,0,1,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(1, KalahaPlayer.NORTH);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.NORTH), 3);
		assertEquals(kb.getStonesInPit(2, KalahaPlayer.NORTH), 1);
		assertEquals(kb.getStonesInPit(2, KalahaPlayer.SOUTH), 0);
	}	
	
	@Test(expectedExceptions=IllegalMoveException.class)
	public void ignoresActionFromPlayerNotInTurn() {		
		kb.moveStones(0, KalahaPlayer.NORTH);
	}
	
	@Test(expectedExceptions=IllegalMoveException.class)
	public void ignoresActionMovingZeroStones() {
		setupState(kb, 0,10,10,0,1,2,7, 10,1,0,12,5,11,3);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
		kb.moveStones(0, KalahaPlayer.SOUTH);
	}
	
	@Test
	public void updatesPlayerToActAfterPlayerActs() {
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
		kb.moveStones(1, KalahaPlayer.SOUTH);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.NORTH);
	}
	
	@Test
	public void playerGetsToActAgainWhenEndingInOwnKalaha() {
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
		kb.setStonesInPit(1, 5, KalahaPlayer.NORTH);
		kb.moveStones(0, KalahaPlayer.SOUTH);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
	}
	
	@Test
	public void playerDoesNotGetToActAgainAfterSteal() {
		KalahaBoard kb = new KalahaBoard(6, KalahaPlayer.NORTH, new BjornRules());
		setupState(kb, 0,10,10,0,1,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(1, KalahaPlayer.NORTH);
		assertEquals(kb.getPlayerToAct(), KalahaPlayer.SOUTH);
	}
	
	@Test
	public void finishingInKalahaHavingNoMoreStonesEndsGame() {
		setupState(kb, 0,0,0,0,0,1,7, 10,1,0,12,5,11,3);		
		kb.moveStones(5, KalahaPlayer.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void eitherPlayerOutOfStonesDoesNotEndsGameWhenSpecialRuleOn() {
		KalahaBoard kb = new KalahaBoard(6, KalahaPlayer.SOUTH, new BjornRules());
		setupState(kb, 0,0,0,0,0,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(5, KalahaPlayer.SOUTH);
		assertFalse(kb.isGameEnded());
	}
	
	@Test
	public void eitherPlayerOutOfStonesDoesEndGameWhenSpecialRuleOff() {
		setupState(kb, 0,0,0,0,0,2,7, 10,1,0,12,5,11,3);		
		kb.moveStones(5, KalahaPlayer.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void endGame() {
		setupState(kb, 1,3,1,1,0,0,30,0,0,0,0,0,0,23);
		kb.moveStones(3, KalahaPlayer.SOUTH);
		assertTrue(kb.isGameEnded());
	}
	
	@Test
	public void endingInKalahaAddsStone() {
		assertEquals(72, sum(6,6,6,6,6,6,0,6,6,6,6,6,6,0));
		kb.moveStones(0, KalahaPlayer.SOUTH);
		assertStones(kb, KalahaPlayer.SOUTH, 0, 7, 7, 7, 7, 7);
		assertEquals(kb.getStonesInKalaha(KalahaPlayer.SOUTH), 1);
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
			board.setStonesInPit(pits[i], i, KalahaPlayer.SOUTH);
			board.setStonesInPit(pits[i+7], i, KalahaPlayer.NORTH);
		}
		board.setStonesInKalaha(pits[6], KalahaPlayer.SOUTH);
		board.setStonesInKalaha(pits[13], KalahaPlayer.NORTH);
	}

	/**
	 * Asserts the stones for a player.
	 * @param kb the board
	 * @param player the player whose stones to check
	 * @param stones  the list of stones, the should be of length 7, last one is kalaha
	 */
	private void assertStones(KalahaBoard kb, KalahaPlayer player, int ... stones) {
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
