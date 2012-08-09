package net.kalahau.data.manager;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;

import org.testng.annotations.Test;

public class SessionManagerImplTest extends JpaTestBase {

	@Test
	public void testGetSession() {
		Session s = sessionManager.getSessionByExternalId("kalle", 43);
		assertNotNull(s);
		//assertNotNull(s.getUserId());
		User u = userManager.getUser(s.getUserId());
		assertEquals(u.getId(), s.getUserId());
		assertEquals(u.getExternalId(), "kalle");
		u = userManager.getUserByExternalId("kalle", s.getOperatorId());
		assertEquals(u.getId(), s.getUserId());
		u = sessionManager.getUserBySession(s.getId());
		assertEquals(u.getId(), s.getUserId());
	}
}
