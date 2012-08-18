package net.kalaha.data.manager;

import static net.kalaha.data.entities.InviteStatus.PENDING;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.Invite;
import net.kalaha.data.entities.InviteStatus;
import net.kalaha.data.entities.User;

@Singleton
public class InviteManagerImpl implements InviteManager {
	
	@Inject
	private SystemTime time;
	
	@Inject
	private Provider<EntityManager> em;
	
	private final Logger log = Logger.getLogger(getClass());

	@Override
	@Transactional
	public Invite invite(User inviter, String extInvitedId) {
		Invite inv = new Invite();
		inv.setStatus(PENDING);
		long now = time.utc();
		inv.setCreated(now);
		inv.setLastModified(now);
		inv.setInviter(inviter);
		inv.setInvitedExtId(extInvitedId);
		em.get().persist(inv);
		return inv;
	}

	@Override
	@Transactional
	public Invite updateInvite(long id, InviteStatus status) {
		Invite inv = em.get().find(Invite.class, id);
		if(inv == null) {
			log.debug("Attempt to update non-exising invite: " + id);
		} else {
			inv.setLastModified(time.utc());
			inv.setStatus(status);
		}
		return inv;
	}
}
