package net.kalaha.data.manager;

import static net.kalaha.data.entities.RequestStatus.*;
import static net.kalaha.data.entities.RequestType.CHALLENGE;
import static net.kalaha.data.entities.RequestType.INVITATION;
import static net.kalaha.data.entities.UserStatus.DELETED;
import static net.kalaha.data.entities.UserStatus.INVITED;
import static net.kalaha.data.entities.UserStatus.LIVE;

import javax.persistence.EntityManager;

import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.Request;
import net.kalaha.data.entities.RequestStatus;
import net.kalaha.data.entities.User;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class RequestManagerImpl implements RequestManager {
	
	@Inject
	private SystemTime time;
	
	@Inject
	private Provider<EntityManager> em;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Inject
	private UserManager users;
	
	@Override
	@Transactional
	public Request challenge(User challenger, User challengee) {
		challenger.getGameStats().incrementSentChallenges();
		challengee.getGameStats().incrementChallengesReceived();
		Request inv = new Request();
		inv.setType(CHALLENGE);
		inv.setStatus(PENDING);
		long now = time.utc();
		inv.setCreated(now);
		inv.setLastModified(now);
		inv.setInviter(challenger);
		inv.setInvitee(challengee);
		em.get().persist(inv);
		return inv;
	}

	@Override
	@Transactional
	public Request invite(User inviter, String extInvitedId, int operatorId) {
		User invitee = users.getUserByExternalId(extInvitedId, operatorId);
		if(invitee == null) {
			inviter.getGameStats().incrementSentInvites();
			invitee = users.createUser(extInvitedId, operatorId, INVITED);
			Request inv = new Request();
			inv.setType(INVITATION);
			inv.setStatus(PENDING);
			long now = time.utc();
			inv.setCreated(now);
			inv.setLastModified(now);
			inv.setInviter(inviter);
			inv.setInvitee(invitee);
			em.get().persist(inv);
			return inv;
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Request updateRequest(long id, RequestStatus status) {
		Request inv = em.get().find(Request.class, id);
		if(inv == null) {
			log.debug("Attempt to update non-exising invite: " + id);
		} else {
			inv.setLastModified(time.utc());
			inv.setStatus(status);
			updateStats(status, inv);
			checkInviteeStatus(status, inv);
		}
		return inv;
	}

	private void checkInviteeStatus(RequestStatus status, Request inv) {
		if(inv.getType() == INVITATION) {
			if(status == ACCEPTED) {
				inv.getInvitee().setStatus(LIVE);
			} else if(status == DENIED || status == TIMED_OUT) {
				inv.getInvitee().setStatus(DELETED);
			}
		}
	}

	private void updateStats(RequestStatus status, Request inv) {
		switch(status) {
			case DENIED : {
				incrementDenied(inv);
			}
			case ACCEPTED : {
				incrementAccepted(inv);
			}
			default : break; // do nothing
		}
	}

	private void incrementAccepted(Request inv) {
		User inviter = inv.getInviter();
		User invitee = inv.getInvitee();
		if(inv.getType() == CHALLENGE) {
			inviter.getGameStats().incrementSentChallengesAccepted();
			invitee.getGameStats().incrementChallengesAccepted();
		} else {
			inviter.getGameStats().incrementSentInvitesAccepted();
		}
	}

	private void incrementDenied(Request inv) {
		User inviter = inv.getInviter();
		User invitee = inv.getInvitee();
		if(inv.getType() == CHALLENGE) {
			inviter.getGameStats().incrementSentChallengesDenied();
			invitee.getGameStats().incrementChallengesDenied();
		} 
	}
}
