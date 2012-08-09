package net.kalahau.data.manager;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;

import org.testng.annotations.Test;

public class UserManagerImplTest extends JpaTestBase {
	
	@Test
	public void testGetSession() {
		Session s = userManager.getSessionByExternalId("kalle", 43);
		assertNotNull(s);
		//assertNotNull(s.getUserId());
		User u = userManager.getUser(s.getUserId());
		assertEquals(u.getId(), s.getUserId());
		assertEquals(u.getExternalId(), "kalle");
		u = userManager.getUserByExternalId("kalle", s.getOperatorId());
		assertEquals(u.getId(), s.getUserId());
		u = userManager.getUserBySession(s.getId());
		assertEquals(u.getId(), s.getUserId());
	}
	
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
}
