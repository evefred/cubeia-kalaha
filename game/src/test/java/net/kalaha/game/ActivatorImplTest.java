package net.kalaha.game;

import java.util.Collection;

import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;

import static org.testng.Assert.*;
import org.testng.annotations.Test;

import com.cubeia.firebase.api.common.Attribute;
import com.cubeia.firebase.api.common.AttributeValue;

public class ActivatorImplTest {

	@Test
	public void testCreateNewChallenge() throws Exception {
		ActivatorImpl i = new ActivatorImpl();
		i.init(new ActivatorContextImpl());
		
		// create user
		User u = i.getUserManager().createUser("kalle", 0);
		
		// "create" table, state and game
		i.getParticipantForRequest(u.getId(), 2, attributes(GameForm.CHALLENGE, -1));
		
		// check game
		Collection<Game> games = i.getGameManager().getMyGames(u, null, null);
		assertEquals(games.size(), 1);
		Game g = games.iterator().next();
		assertEquals(g.getOwner(), u);
		assertEquals(g.getForm(), GameForm.CHALLENGE);
	}
	
	@Test
	public void testCreateExistingLive() throws Exception {
		ActivatorImpl i = new ActivatorImpl();
		i.init(new ActivatorContextImpl());
		
		// create user
		User u = i.getUserManager().createUser("olle", 0);
		
		// create "existing" game
		Game g = i.getGameManager().createGame(GameType.KALAHA, GameForm.LIVE, u, null, -1, null);
		
		// create table for the above game
		i.getParticipantForRequest(u.getId(), 2, attributes(GameForm.LIVE, g.getId()));
		
		// check game
		Collection<Game> games = i.getGameManager().getMyGames(u, null, null);
		assertEquals(games.size(), 1);
		Game tmp = games.iterator().next();
		assertEquals(tmp.getOwner(), u);
		assertEquals(tmp.getForm(), GameForm.LIVE);
		assertEquals(tmp, g);
	}

	private Attribute[] attributes(GameForm challenge, int gameId) {
		if(gameId == -1) {
			return new Attribute[] { new Attribute("form", new AttributeValue(challenge.toString().toLowerCase())) };
		} else {
			return new Attribute[] { 
					     new Attribute("form", new AttributeValue(challenge.toString().toLowerCase())),
					     new Attribute("gameId", new AttributeValue(gameId))
					   }; 
		}
	}
}
