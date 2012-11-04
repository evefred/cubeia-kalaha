package net.kalaha.data.manager;

import static net.kalaha.data.entities.GameType.KALAHA;
import static net.kalaha.data.entities.RequestStatus.ACCEPTED;
import static net.kalaha.data.entities.RequestStatus.DENIED;
import static net.kalaha.data.entities.RequestStatus.PENDING;
import static net.kalaha.data.entities.RequestStatus.TIMED_OUT;
import static net.kalaha.data.entities.RequestType.CHALLENGE;
import static net.kalaha.data.entities.RequestType.INVITATION;
import static net.kalaha.data.entities.UserStatus.DELETED;
import static net.kalaha.data.entities.UserStatus.INVITED;
import static net.kalaha.data.entities.UserStatus.LIVE;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

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
@Transactional
public class RequestManagerImpl implements RequestManager {
	
	@Inject
	private SystemTime time;
	
	@Inject
	private Provider<EntityManager> em;
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Inject
	private UserManager users;
	
	@Inject
	private GameManager games;
	
	@Override
	public Request challenge(User challenger, User challengee, String extId) {
		// challenger.getGameStats().incrementSentChallenges();
		// challengee.getGameStats().incrementChallengesReceived();
		Request inv = new Request();
		inv.setExternalRequestId(extId);
		inv.setType(CHALLENGE);
		inv.setStatus(PENDING);
		long now = time.utc();
		inv.setCreated(now);
		inv.setLastModified(now);
		inv.setInviter(challenger);
		inv.setInvitee(challengee);
		em.get().persist(inv);
		
		// create game
		games.createGame(KALAHA, inv);
		
		return inv;
	}

	@Override
	public Request invite(User inviter, String extInvitedId, int operatorId, String extId) {
		User invitee = users.getUserByExternalId(extInvitedId, operatorId);
		if(invitee == null) {
			inviter.getGameStats().incrementSentInvites();
			invitee = users.createUser(extInvitedId, operatorId, INVITED);
			Request inv = new Request();
			inv.setExternalRequestId(extId);
			inv.setType(INVITATION);
			inv.setStatus(PENDING);
			long now = time.utc();
			inv.setCreated(now);
			inv.setLastModified(now);
			inv.setInviter(inviter);
			inv.setInvitee(invitee);
			em.get().persist(inv);
			
			// create game
			games.createGame(KALAHA, inv);
			
			return inv;
		} else {
			return null;
		}
	}
	
	@Override
	public Request getRequestByExternalId(String extId) {
		try {
			Query q = em.get().createQuery("from Request q where q.externalRequestId = :extId");
			q.setParameter("extId", extId);
			return (Request) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public Request updateRequestByExternalId(String extId, RequestStatus status) {
		Request req = getRequestByExternalId(extId);
		if(req == null) {
			log.debug("Attempt to update non-exising invite by extId: " + extId);
		} else {
			req = updateRequest(req.getId(), status);
		}
		return req;
	}
	
	@Override
	public Request updateRequest(long id, RequestStatus status) {
		Request inv = em.get().find(Request.class, id);
		if(inv == null) {
			log.debug("Attempt to update non-exising invite: " + id);
		} else {
			inv.setLastModified(time.utc());
			inv.setStatus(status);
			updateStats(status, inv);
			checkInviteeStatus(status, inv);
			updateGame(inv);
		}
		return inv;
	}
	
	
	// --- PRIVATE METHODS --- ///

	private void updateGame(Request inv) {
		games.updateGame(inv, inv.getStatus() == ACCEPTED);
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
		// User invitee = inv.getInvitee();
		if(inv.getType() == CHALLENGE) {
			// inviter.getGameStats().incrementSentChallengesAccepted();
			// invitee.getGameStats().incrementChallengesAccepted();
		} else {
			inviter.getGameStats().incrementSentInvitesAccepted();
		}
	}

	private void incrementDenied(Request inv) {
		/*User inviter = inv.getInviter();
		User invitee = inv.getInvitee();
		if(inv.getType() == CHALLENGE) {
			inviter.getGameStats().incrementSentChallengesDenied();
			invitee.getGameStats().incrementChallengesDenied();
		} */
	}
}
