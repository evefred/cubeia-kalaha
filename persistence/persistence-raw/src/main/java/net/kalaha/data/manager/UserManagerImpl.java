package net.kalaha.data.manager;

import static net.kalaha.data.entities.UserStatus.LIVE;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.GameStats;
import net.kalaha.data.entities.GameStats.Field;
import net.kalaha.data.entities.User;
import net.kalaha.data.entities.UserDetails;
import net.kalaha.data.entities.UserStatus;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserManagerImpl implements UserManager {
		
	@Inject
	private Provider<EntityManager> em;
	
	@Inject
	private SystemTime time;
	
	@Override
	public void setDisplayName(long id, String displayName) {
		User user = getUser(id);
		if(user != null) {
			user.getUserDetails().setDisplayName(displayName);
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUserByStats(int limit, Field field, Set<Long> userGroup) {
		StringBuilder b = new StringBuilder("select u from User u");
		if(userGroup != null) {
			b.append(" where id in (:users)");
		}
		if(field != null) {
			b.append(" order by ").append("gameStats." + field.getFieldName()).append(" desc");
		}
		Query q = em.get().createQuery(b.toString());
		if(userGroup != null) {
			q.setParameter("users", userGroup);
		}
		if(limit > 0) {
			q.setMaxResults(limit);
		}
		return q.getResultList();
	}
	
	
	@Override
	public User createUser(String extId, int operatorId, UserStatus st) {
		User u = getUserByExternalId(extId, operatorId);
		if(u == null) {
			u = doCreateUser(extId, operatorId, st);
		} else {
			u.setStatus(st);
		}
		return u;
	}
	
	@Override
	public User createLocalUser(String localName, String password) {
		User u = getUserByLocalName(localName);
		if(u == null) {
			u = doCreateLocalUser(localName, password, 0);
		}
		return u;
	}

	@Override
	public User getUser(long id) {
		return em.get().find(User.class, id);
	}

	@Override
	public User getUserByExternalId(String extId, int operatorId) {
		return doGetUserByExternalId(extId, operatorId);
	}
	
	@Override
	public User getUserByLocalName(String userName) {
		return doGetUserByLocalName(userName, 0);
	}
	
	@Override
	public User authLocalUser(String localName, String passwd) {
		return doAuthLocalUser(localName, passwd);
	}
	
	@Override
	public User authBot(String userName, int botId) {
		return doAuthBot(userName, botId);
	}


	// --- PRIVATE METHODS --- //
	
	private User doAuthBot(String userName, int botId) {
		User u = doGetUserByLocalName(userName, 666);
		if(u == null) {
			u = doCreateLocalUser(userName, "Bot_" + botId, 666);
		}
		return u;
	}
	
	private User doAuthLocalUser(String localName, String passwd) {
		Query q = em.get().createQuery("select t from User t where t.localName = :localName and t.localPassword = :localPassword");
		q.setParameter("localName", localName);
		q.setParameter("localPassword", passwd);
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	private User doGetUserByExternalId(String extId, int operatorId) {
		Query q = em.get().createQuery("select t from User t where t.externalId = :extId and t.operatorId = :opId");
		q.setParameter("extId", extId);
		q.setParameter("opId", operatorId);
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	private User doGetUserByLocalName(String userName, int operatorId) {
		Query q = em.get().createQuery("select t from User t where t.localName = :localName and t.operatorId = :operatorId");
		q.setParameter("localName", userName);
		q.setParameter("operatorId", operatorId);
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	private User doCreateUser(String extId, int operatorId, UserStatus st) {
		User s = newUser(st);
		GameStats stats = new GameStats();
		stats.setEloRating(1500);
		em.get().persist(stats);
		UserDetails det = new UserDetails();
		em.get().persist(det);
		s.setGameStats(stats);
		s.setUserDetails(det);
		s.setExternalId(extId);
		s.setOperatorId(operatorId);
		em.get().persist(s);
		return s;
	}
	
	private User doCreateLocalUser(String localName, String password, int operatorId) {
		User s = newUser(LIVE);
		GameStats stats = new GameStats();
		stats.setEloRating(1500);
		em.get().persist(stats);
		UserDetails det = new UserDetails();
		det.setDisplayName(localName);
		em.get().persist(det);
		s.setGameStats(stats);
		s.setUserDetails(det);
		s.setOperatorId(operatorId);
		s.setLocalName(localName);
		s.setLocalPassword(password);
		em.get().persist(s);
		return s;
	}

	private User newUser(UserStatus st) {
		User s = new User();
		s.setStatus(st);
		long t = time.utc();
		s.setCreated(t);
		s.setLastModified(t);
		return s;
	}
}
