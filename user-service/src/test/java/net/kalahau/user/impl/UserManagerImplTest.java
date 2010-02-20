package net.kalahau.user.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import net.kalaha.user.api.Session;
import net.kalaha.user.api.User;

import org.testng.annotations.Test;

public class UserManagerImplTest extends JpaTestBase {
	
	@Test
	public void testGetSession() {
		Session s = manager.getSessionByExternalId("kalle", 43);
		assertNotNull(s);
		//assertNotNull(s.getUserId());
		User u = manager.getUser(s.getUserId());
		assertEquals(u.getId(), s.getUserId());
		assertEquals(u.getExternalId(), "kalle");
		u = manager.getUserByExternalId("kalle", s.getOperatorId());
		assertEquals(u.getId(), s.getUserId());
		u = manager.getUserBySession(s.getId());
		assertEquals(u.getId(), s.getUserId());
	}
}
