package net.kalaha.entities;

import java.util.Arrays;

import net.kalaha.data.entities.GameState;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GameStateTest {

	@Test
	public void testSetRealState() {
		GameState s = new GameState();
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
		s.setRealState(a);
		Assert.assertEquals(s.getState(), "1,2,3,4,5,6,7,8,9,10,11,12,13,14");
	}
	
	@Test
	public void testSetState() {
		GameState s = new GameState();
		int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
		s.setState("1,2,3,4,5,6,7,8,9,10,11,12,13,14");
		Assert.assertTrue(Arrays.equals(s.getRealState(), a));
	}
}
