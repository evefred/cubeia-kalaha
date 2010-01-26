package net.kalaha.game.json;

import org.testng.Assert;
import org.testng.annotations.Test;

import net.kalaha.game.action.Sow;

public class ActionTransformerTest {

	@Test
	public void testToString() {
		Sow s = new Sow();
		s.setPlayerId(99);
		s.setHouse(2);
		String string = ActionTransformer.toString(s);
		Assert.assertEquals(string, "[\"Sow\",{\"house\":2,\"playerId\":99}]");
	}
	
	@Test
	public void fromString() {
		String json = "[\"Sow\",{\"house\":2,\"playerId\":99}]";
		Sow test = new Sow();
		test.setPlayerId(99);
		test.setHouse(2);
		Object action = ActionTransformer.fromString(json);
		Assert.assertEquals(action, test);
	}
}
