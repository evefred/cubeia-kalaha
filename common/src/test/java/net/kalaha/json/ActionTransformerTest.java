package net.kalaha.json;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ActionTransformerTest {

	@Test
	public void testToAndFromString() {
		TestAction s = new TestAction();
		s.setPlayerId(99);
		s.setHouse(2);
		String json = new Transformer().toString(s);
		// Assert.assertEquals(string, "{\"_action\":\"TestAction\",\"house\":2,\"playerId\":99}");
		Object action = new Transformer().fromString(json);
		Assert.assertEquals(action, s);
	}
	
	@Test
	public void fromString() {
		String json = "{\"_action\":\"TestAction\",\"house\":2,\"playerId\":99}";
		TestAction test = new TestAction();
		test.setPlayerId(99);
		test.setHouse(2);
		Object action = new Transformer().fromString(json);
		Assert.assertEquals(action, test);
	}
}
