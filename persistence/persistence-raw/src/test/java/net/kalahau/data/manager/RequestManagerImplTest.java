package net.kalahau.data.manager;

import static net.kalaha.data.entities.RequestStatus.ACCEPTED;
import static net.kalaha.data.entities.RequestStatus.DENIED;
import static net.kalaha.data.entities.RequestStatus.PENDING;
import static net.kalaha.data.entities.UserStatus.DELETED;
import static net.kalaha.data.entities.UserStatus.INVITED;
import static net.kalaha.data.entities.UserStatus.LIVE;
import static org.testng.Assert.assertEquals;
import net.kalaha.data.entities.Request;
import net.kalaha.data.entities.User;

import org.testng.annotations.Test;

public class RequestManagerImplTest extends JpaTestBase {

	@Test
	public void testCreateInvite() {
		SystemTestTime.TIME.set(10);
		User u = userManager.createUser("kalle", 32, LIVE);
		Request inv = inviteManager.invite(u, "xxx", 32, "yyy");
		assertEquals(inv.getInviter(), u);
		assertEquals(inv.getInvitee().getExternalId(), "xxx");
		assertEquals(inv.getCreated(), 10);
		assertEquals(inv.getLastModified(), 10);
		assertEquals(inv.getStatus(), PENDING);
		assertEquals(inv.getInvitee().getStatus(), INVITED);
		assertEquals(inv.getInviter().getGameStats().getSentInvites(), 1);
	}
	
	@Test
	public void testCreateChallenge() {
		SystemTestTime.TIME.set(10);
		User u1 = userManager.createUser("kalle1", 32, LIVE);
		User u2 = userManager.createUser("kalle2", 32, LIVE);
		Request inv = inviteManager.challenge(u1, u2, "yyy");
		assertEquals(inv.getInviter(), u1);
		assertEquals(inv.getInvitee(), u2);
		assertEquals(inv.getStatus(), PENDING);
		assertEquals(inv.getInviter().getGameStats().getSentChallenges(), 1);
		assertEquals(inv.getInvitee().getGameStats().getChallengesReceived(), 1);
	}
	
	@Test
	public void testInviteAccepted() {
		SystemTestTime.TIME.set(10);
		User u = userManager.createUser("kalle", 32, LIVE);
		Request inv = inviteManager.invite(u, "xxx", 32, "yyy");
		SystemTestTime.TIME.set(20);
		inv = inviteManager.updateRequest(inv.getId(), ACCEPTED);
		assertEquals(inv.getStatus(), ACCEPTED);
		assertEquals(inv.getCreated(), 10);
		assertEquals(inv.getLastModified(), 20);
		User u2 = userManager.getUser(inv.getInvitee().getId());
		assertEquals(u2.getStatus(), LIVE);
		assertEquals(inv.getInviter().getGameStats().getSentInvitesAccepted(), 1);
	}
	
	@Test
	public void testInviteDeclined() {
		SystemTestTime.TIME.set(10);
		User u = userManager.createUser("kalle", 32, LIVE);
		Request inv = inviteManager.invite(u, "xxx", 32, "yyy");
		inv = inviteManager.updateRequestByExternalId("yyy", DENIED);
		User u2 = userManager.getUser(inv.getInvitee().getId());
		assertEquals(u2.getStatus(), DELETED);
	}
	
	@Test
	public void testChallengeAccepted() {
		SystemTestTime.TIME.set(10);
		User u1 = userManager.createUser("kalle1", 32, LIVE);
		User u2 = userManager.createUser("kalle2", 32, LIVE);
		Request inv = inviteManager.challenge(u1, u2, "yyy");
		inv = inviteManager.updateRequest(inv.getId(), ACCEPTED);
		assertEquals(inv.getStatus(), ACCEPTED);
		assertEquals(inv.getInviter().getGameStats().getSentChallengesAccepted(), 1);
		assertEquals(inv.getInvitee().getGameStats().getChallengesAccepted(), 1);
	}
	
	@Test
	public void testChallengeDenied() {
		SystemTestTime.TIME.set(10);
		User u1 = userManager.createUser("kalle1", 32, LIVE);
		User u2 = userManager.createUser("kalle2", 32, LIVE);
		Request inv = inviteManager.challenge(u1, u2, "yyy");
		inv = inviteManager.updateRequest(inv.getId(), DENIED);
		assertEquals(inv.getStatus(), DENIED);
		assertEquals(inv.getInviter().getGameStats().getSentChallengesDeclined(), 1);
		assertEquals(inv.getInvitee().getGameStats().getChallengesDeclined(), 1);
	}
}
