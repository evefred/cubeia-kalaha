package net.kalaha.common.elo;

import static net.kalaha.common.elo.EloCalculator.Result.PLAYER_ONE;
import static net.kalaha.common.elo.EloCalculator.Result.PLAYER_TWO;
import static net.kalaha.common.util.Reflection.setPrivateDeclaredField;
import static org.testng.Assert.assertEquals;
import net.kalaha.common.elo.EloCalculator.Matchup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DefaultEloCalculatorTest {

	private DefaultEloCalculator calc;
	
	@BeforeMethod
	public void setup() {
		KSelector sel = new UscfKSelector();
		calc = new DefaultEloCalculator();
		setPrivateDeclaredField(calc, "kSelector", sel);
	}
	
	@Test
	public void testSimple() {
		Matchup res = calc.calculate(PLAYER_ONE, new Matchup(1500, 1432));
		assertEquals(Math.round(res.playerOne), 1513);
		assertEquals(Math.round(res.playerTwo), 1419);
	}
	
	@Test
	public void testDifferentK() {
		Matchup res = calc.calculate(PLAYER_TWO, new Matchup(2200, 1432));
		assertEquals(Math.round(res.playerOne), 2176);
		assertEquals(Math.round(res.playerTwo), 1464);
	}
}
