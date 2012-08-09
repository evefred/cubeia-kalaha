package net.kalaha.data.entities;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.User;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GameTest {

	@Test
	public void getMyGame() {
		User u1 = new User();
		u1.setId(1);
		
		User u2 = new User();
		u2.setId(2);
		
		Game g = new Game();
		g.setOwner(u1);
		g.setOpponent(u2);
		
		Assert.assertEquals(g.getMyOpponent(u1), u2);
		Assert.assertEquals(g.getMyOpponent(u2), u1);
		
	}
}
