package net.kalahau.data.manager;

import static net.kalaha.data.entities.GameResult.DRAW;
import static net.kalaha.data.entities.GameResult.WIN;
import static net.kalaha.data.entities.GameStatus.ACTIVE;
import static net.kalaha.data.entities.GameStatus.CANCELLED;
import static net.kalaha.data.entities.GameStatus.FINISHED;
import static net.kalaha.data.entities.GameStatus.PENDING;
import static net.kalaha.data.entities.RequestStatus.ACCEPTED;
import static net.kalaha.data.entities.RequestStatus.DENIED;
import static net.kalaha.data.entities.UserStatus.LIVE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameResult;
import net.kalaha.data.entities.GameStats;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.GameType;
import net.kalaha.data.entities.Request;
import net.kalaha.data.entities.User;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GameManagerImplTest extends JpaTestBase {
	
	@Test
	public void testCreateGetGame() {
		// create users and game
		User me = userManager.createUser("larsan", 0, LIVE);
		User kalle = userManager.createUser("kalle", 0, LIVE);
		Game g1 = gameManager.createGame(GameType.KALAHA, me, kalle, 100, null);
		// check retrieval
		Game g2 = gameManager.getGame(g1.getId());
		assertEquals(g2, g1);
		// check null on dummy id
		Game g3 = gameManager.getGame(-1);
		assertNull(g3);
	}
	
	@Test
	private void testInviteAndAccept() {
		User me = userManager.createUser("larsan", 0, LIVE);
		// User kalle = userManager.createUser("kalle", 0, LIVE);
		Request invite = requestManager.invite(me, "666", 0, "1");
		Game g = gameManager.getGame(invite);
		assertEquals(g.getRequest(), invite);
		assertEquals(g.getStatus(), PENDING);
		requestManager.updateRequest(invite.getId(), ACCEPTED);
		g = gameManager.getGame(invite);
		assertEquals(g.getStatus(), ACTIVE);
	}
	
	@Test
	private void testInviteAndDecline() {
		User me = userManager.createUser("larsan", 0, LIVE);
		// User kalle = userManager.createUser("kalle", 0, LIVE);
		Request invite = requestManager.invite(me, "666", 0, "1");
		Game g = gameManager.getGame(invite);
		assertEquals(g.getRequest(), invite);
		assertEquals(g.getStatus(), PENDING);
		requestManager.updateRequest(invite.getId(), DENIED);
		g = gameManager.getGame(invite);
		assertEquals(g.getStatus(), CANCELLED);
	}
	
	@Test
	public void testFinishGame() {
		// create users and game
		User me = userManager.createUser("larsan", 0, LIVE);
		User kalle = userManager.createUser("kalle", 0, LIVE);
		Game g1 = gameManager.createGame(GameType.KALAHA, me, kalle, 100, null);
		assertEquals(g1.getStatus(), ACTIVE);
		// finish game	
		Game g2 = gameManager.finishGame(g1.getId(), me.getId(), WIN);
		assertEquals(g2.getStatus(), FINISHED);
		GameStats one = me.getGameStats();
		GameStats two = kalle.getGameStats();
		assertEquals(one.getGamesWon(), 1);
		assertEquals(two.getGamesWon(), 0);
		assertEquals(one.getGamesLost(), 0);
		assertEquals(two.getGamesLost(), 1);
		assertEquals(one.getTotalGames(), 1);
		assertEquals(two.getTotalGames(), 1);
		assertEquals(Math.round(one.getEloRating()), 1516);
		assertEquals(Math.round(two.getEloRating()), 1484);
	}

	@Test
	public void testSearchGame() {
		User me = userManager.createUser("larsan", 0, LIVE);
		User kalle = userManager.createUser("kalle", 0, LIVE);
		
		Game g1 = gameManager.createGame(GameType.KALAHA, me, kalle, 100, null);
		Game g2 = gameManager.createGame(GameType.KALAHA, me, kalle, 100, null);
		Game g3 = gameManager.createGame(GameType.KALAHA, me, null, 100, null);
		Map<Long, Game> games = new HashMap<Long, Game>();
		games.put(g1.getId(), g1);
		games.put(g2.getId(), g2);
		games.put(g3.getId(), g3);

		// get all my games
		Collection<Game> myGames = gameManager.getMyGames(me, null);
		Assert.assertEquals(myGames.size(), 3);
		for (Game g : myGames) {
			Game tmp = games.get(g.getId());
			Assert.assertEquals(g, tmp);
		}	
		
		// set me as winner on g1
		gameManager.finishGame(g1.getId(), me.getId(), WIN);
		
		// get all my finished games
		myGames = gameManager.getMyGames(me, GameStatus.FINISHED);
		Assert.assertEquals(myGames.size(), 1);
		for (Game g : myGames) {
			Assert.assertEquals(me.getId(), g.getWinningUser());
		}
		
		// set draw on g2
		gameManager.finishGame(g2.getId(), -1, DRAW);
		
		// get all my finished (1 win and one draw)
		myGames = gameManager.getMyGames(me, GameStatus.FINISHED);
		Assert.assertEquals(myGames.size(), 2);
		for (Game g : myGames) {
			if(g.getResult() == GameResult.DRAW) {
				Assert.assertEquals(0, g.getWinningUser());
			} else {
				Assert.assertEquals(me.getId(), g.getWinningUser());
			}
		}
	}
}
