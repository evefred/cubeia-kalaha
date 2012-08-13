package net.kalaha.game;

import static com.cubeia.firebase.api.game.lobby.DefaultTableAttributes._LAST_MODIFIED;
import static com.cubeia.firebase.api.game.lobby.DefaultTableAttributes._SEATED;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cubeia.firebase.api.common.AttributeValue;

public class ActivatorTableFilterTest {

	private SystemTestTime time;
	private ActivatorTableFilter filter;

	@BeforeMethod
	public void setup() {
		time = new SystemTestTime();
		filter = new ActivatorTableFilter(time, 10);
		SystemTestTime.TIME.set(10);
	}
	
	@Test
	public void testOnlyOld() {
		Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
		map.put(_LAST_MODIFIED.name(), AttributeValue.wrap(15));
		map.put(_SEATED.name(), AttributeValue.wrap(1));
		SystemTestTime.TIME.set(26);
		// old but have seated player
		assertFalse(filter.accept(map));
	}
	
	@Test
	public void testOnlyEmpty() {
		Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
		map.put(_LAST_MODIFIED.name(), AttributeValue.wrap(15));
		map.put(_SEATED.name(), AttributeValue.wrap(0));
		SystemTestTime.TIME.set(16);
		// no seated but not old either
		assertFalse(filter.accept(map));
	}
	
	@Test
	public void testEmptyAndOld() {
		Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
		map.put(_LAST_MODIFIED.name(), AttributeValue.wrap(15));
		map.put(_SEATED.name(), AttributeValue.wrap(0));
		SystemTestTime.TIME.set(26);
		// both old and empty
		assertTrue(filter.accept(map));
	}
}
