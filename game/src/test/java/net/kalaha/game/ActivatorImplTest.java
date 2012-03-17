package net.kalaha.game;

import static org.testng.Assert.assertEquals;
import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;
import net.kalaha.table.api.GetTableRequest;

import org.testng.annotations.Test;

import com.cubeia.firebase.api.routing.ActivatorAction;

public class ActivatorImplTest {

	/*@Test
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
	}*/
	
	@Test
	public void testCreateExistingLive() throws Exception {
		ActivatorContextImpl con = new ActivatorContextImpl();
		ActivatorImpl i = new ActivatorImpl();
		i.init(con);
		
		// create user
		User u = i.getUserManager().createUser("olle", 0);
		
		// create "existing" game
		Game g = i.getGameManager().createGame(GameType.KALAHA, GameForm.LIVE, u, null, -1, null);
		
		// create table for the above game
		GetTableRequest q = new GetTableRequest(u.getId(), 2, g.getId());
		ActivatorAction<GetTableRequest> a = new ActivatorAction<GetTableRequest>(q);
		i.onAction(a);
		//i.getParticipantForRequest(u.getId(), 2, attributes(GameForm.LIVE, g.getId()));
		
		// check table id
		assertEquals(con.foundTableId, 666);
	}

	/*private Attribute[] attributes(GameForm challenge, int gameId) {
		if(gameId == -1) {
			return new Attribute[] { new Attribute("form", new AttributeValue(challenge.toString().toLowerCase())) };
		} else {
			return new Attribute[] { 
					     new Attribute("form", new AttributeValue(challenge.toString().toLowerCase())),
					     new Attribute("gameId", new AttributeValue(gameId))
					   }; 
		}
	}*/
}
