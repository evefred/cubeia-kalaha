package net.kalahau.data.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameResult;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;

public class GameManagerImplTest extends JpaTestBase {

	@Test
	public void testCreateGame() {
		User me = userManager.createUser("larsan", 0);
		User kalle = userManager.createUser("kalle", 0);
		
		Game g1 = gameManager.createGame(GameType.KALAHA, GameForm.CHALLENGE, me, kalle, 100, null);
		Game g2 = gameManager.createGame(GameType.KALAHA, GameForm.LIVE, me, kalle, 100, null);
		Game g3 = gameManager.createGame(GameType.KALAHA, GameForm.CHALLENGE, me, null, 100, null);
		Map<Integer, Game> games = new HashMap<Integer, Game>();
		games.put(g1.getId(), g1);
		games.put(g2.getId(), g2);
		games.put(g3.getId(), g3);

		// get all my games
		Collection<Game> myGames = gameManager.getMyGames(me, null, null);
		Assert.assertEquals(myGames.size(), 3);
		for (Game g : myGames) {
			Game tmp = games.get(g.getId());
			Assert.assertEquals(g, tmp);
		}	
		
		// get all my challenge games
		myGames = gameManager.getMyGames(me, null, GameForm.CHALLENGE);
		Assert.assertEquals(myGames.size(), 2);
		for (Game g : myGames) {
			Game tmp = games.get(g.getId());
			Assert.assertEquals(g, tmp);
		}	
		
		// get kalles challenge games
		myGames = gameManager.getMyGames(kalle, null, GameForm.CHALLENGE);
		Assert.assertEquals(myGames.size(), 1);
		for (Game g : myGames) {
			Game tmp = games.get(g.getId());
			Assert.assertEquals(g, tmp);
		}	
		
		// set me as winner on g1
		gameManager.finishGame(g1.getId(), me);
		
		// get all my finished games
		myGames = gameManager.getMyGames(me, GameStatus.FINISHED, null);
		Assert.assertEquals(myGames.size(), 1);
		for (Game g : myGames) {
			Assert.assertEquals(me.getId(), g.getWinningUser());
		}
		
		// set draw on g2
		gameManager.finishGame(g2.getId(), null);
		
		// get all my finished (1 win and one draw)
		myGames = gameManager.getMyGames(me, GameStatus.FINISHED, null);
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
