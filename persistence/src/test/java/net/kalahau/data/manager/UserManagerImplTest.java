package net.kalahau.data.manager;

import static net.kalaha.data.entities.GameStats.Field.ELO_RANKING;
import static net.kalaha.data.entities.GameStats.Field.FRIENDS;
import static net.kalaha.data.entities.GameStats.Field.GAMES_WON;
import static net.kalaha.data.entities.GameStats.Field.TOTAL_GAMES;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.kalaha.data.entities.User;

import org.testng.annotations.Test;

import com.google.inject.persist.Transactional;

public class UserManagerImplTest extends JpaTestBase {
	
	@Test
	public void testUser() {
		User u = userManager.createUser("kalle", 32);
		assertNotNull(userManager.getUser(u.getId()));
		assertEquals(userManager.getUserByExternalId("kalle", 32), u);
	}
	
	@Test
	public void testLocalUser() {
		User u = userManager.createLocalUser("kalle", "kalle");
		assertNotNull(userManager.getUser(u.getId()));
		assertEquals(userManager.getUserByLocalName("kalle"), u);
	}
	
	@Test
	public void testSetDisplayName() {
		User u = userManager.createLocalUser("kalle", "kalle");
		assertNotNull(userManager.getUser(u.getId()));
		assertEquals(userManager.getUserByLocalName("kalle").getUserDetails().getDisplayName(), "kalle");
		userManager.setDisplayName(u.getId(), "kalle1");
		assertEquals(userManager.getUserByLocalName("kalle").getUserDetails().getDisplayName(), "kalle1");
	}
	
	@Test
	public void testOrderByTotalGames() {
		User u1 = userManager.createLocalUser("kalle1", "kalle");
		User u2 = userManager.createLocalUser("kalle2", "kalle");
		User u3 = userManager.createLocalUser("kalle3", "kalle");
		updateTotalGames(u1, 1);
		updateTotalGames(u2, 2);
		updateTotalGames(u3, 3);
		List<User> list = userManager.listUserByStats(0, TOTAL_GAMES, null);
		assertEquals(list.size(), 3);
		assertEquals(list.get(0), u3);
		assertEquals(list.get(0).getGameStats().getTotalGames(), 3);
		assertEquals(list.get(1), u2);
		assertEquals(list.get(1).getGameStats().getTotalGames(), 2);
		assertEquals(list.get(2), u1);
		assertEquals(list.get(2).getGameStats().getTotalGames(), 1);
	}
	
	@Test
	public void testOrderAndLimit() {
		User u1 = userManager.createLocalUser("kalle1", "kalle");
		User u2 = userManager.createLocalUser("kalle2", "kalle");
		User u3 = userManager.createLocalUser("kalle3", "kalle");
		updateGamesWon(u1, 1);
		updateGamesWon(u2, 2);
		updateGamesWon(u3, 3);
		List<User> list = userManager.listUserByStats(2, GAMES_WON, null);
		assertEquals(list.size(), 2);
		assertEquals(list.get(0), u3);
		assertEquals(list.get(1), u2);
	}
	
	@Test
	public void testOrderAndGroup() {
		User u1 = userManager.createLocalUser("kalle1", "kalle");
		User u2 = userManager.createLocalUser("kalle2", "kalle");
		User u3 = userManager.createLocalUser("kalle3", "kalle");
		User u4 = userManager.createLocalUser("kalle4", "kalle");
		updateFriends(u1, 1);
		updateFriends(u2, 2);
		updateFriends(u3, 3);
		updateFriends(u4, 4);
		Set<Integer> set = new HashSet<Integer>();
		set.add(u4.getId());
		set.add(u2.getId());
		set.add(u1.getId());
		List<User> list = userManager.listUserByStats(0, FRIENDS, set);
		assertEquals(list.size(), 3);
		assertEquals(list.get(0), u4);
		assertEquals(list.get(1), u2);
		assertEquals(list.get(2), u1);
	}
	
	@Test
	public void testOrderAndGroupAndLimit() {
		User u1 = userManager.createLocalUser("kalle1", "kalle");
		User u2 = userManager.createLocalUser("kalle2", "kalle");
		User u3 = userManager.createLocalUser("kalle3", "kalle");
		User u4 = userManager.createLocalUser("kalle4", "kalle");
		updateRanking(u1, 1);
		updateRanking(u2, 2);
		updateRanking(u3, 3);
		updateRanking(u4, 4);
		Set<Integer> set = new HashSet<Integer>();
		set.add(u4.getId());
		set.add(u2.getId());
		set.add(u1.getId());
		List<User> list = userManager.listUserByStats(2, ELO_RANKING, set);
		assertEquals(list.size(), 2);
		assertEquals(list.get(0), u4);
		assertEquals(list.get(1), u2);
	}

	
	// --- PRIVATE METHODS --- //
	
	@Transactional
	private void updateTotalGames(User u, int i) {
		u.getGameStats().setTotalGames(i);
	}
	
	@Transactional
	private void updateGamesWon(User u, int i) {
		u.getGameStats().setGamesWon(i);
	}
	
	@Transactional
	private void updateFriends(User u, int i) {
		u.getGameStats().setFriends(i);
	}
	
	@Transactional
	private void updateRanking(User u, int i) {
		u.getGameStats().setEloRating(i);
	}
}
