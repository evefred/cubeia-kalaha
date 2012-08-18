package net.kalahau.data.manager;

import static net.kalaha.data.entities.InviteStatus.ACCEPTED;
import static net.kalaha.data.entities.InviteStatus.PENDING;
import static org.testng.Assert.assertEquals;
import net.kalaha.data.entities.Invite;
import net.kalaha.data.entities.User;

import org.testng.annotations.Test;

public class InviteManagerImplTest extends JpaTestBase {

	@Test
	public void testCreate() {
		SystemTestTime.TIME.set(10);
		User u = userManager.createUser("kalle", 32);
		Invite inv = inviteManager.invite(u, "xxx");
		assertEquals(inv.getInviter(), u);
		assertEquals(inv.getInvitedExtId(), "xxx");
		assertEquals(inv.getCreated(), 10);
		assertEquals(inv.getLastModified(), 10);
		assertEquals(inv.getStatus(), PENDING);
	}
	
	@Test
	public void testUpdate() {
		SystemTestTime.TIME.set(10);
		User u = userManager.createUser("kalle", 32);
		Invite inv = inviteManager.invite(u, "xxx");
		SystemTestTime.TIME.set(20);
		inv = inviteManager.updateInvite(inv.getId(), ACCEPTED);
		assertEquals(inv.getStatus(), ACCEPTED);
		assertEquals(inv.getCreated(), 10);
		assertEquals(inv.getLastModified(), 20);
	}
}
