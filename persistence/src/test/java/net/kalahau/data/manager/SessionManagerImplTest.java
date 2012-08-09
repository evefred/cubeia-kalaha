package net.kalahau.data.manager;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.List;

import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;

import org.testng.annotations.Test;

public class SessionManagerImplTest extends JpaTestBase {
	
	@Test
	public void testReaping() {
		sessionManager.getSessionByExternalId("kalle", 43);
		sessionManager.getSessionByExternalId("olle", 43);
		// advance time and create 3rd
		SystemTestTime.TIME.set(10);
		sessionManager.getSessionByExternalId("pelle", 43);
		// advance time and count
		SystemTestTime.TIME.set(15);
		long count = sessionManager.countSessions();
		assertEquals(count, 3);
		// reap and count
		List<Session> reaped = sessionManager.reapSessions(12);
		assertEquals(reaped.size(), 2);
		assertTrue(sessionsContainsExtId(reaped, "kalle"));
		assertTrue(sessionsContainsExtId(reaped, "olle"));
		count = sessionManager.countSessions();
		assertEquals(count, 1);
	}

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
	
	
	// --- PRIVATE METHODS --- //
	
	private boolean sessionsContainsExtId(List<Session> sessions, String extId) {
		for (Session s : sessions) {
			if(extId.equals(s.getExternalId())) {
				return true;
			}
		}
		return false;
	}
}
