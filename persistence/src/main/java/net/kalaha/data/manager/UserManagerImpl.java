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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class UserManagerImpl implements UserManager {
		
	@Inject
	private Provider<EntityManager> em;
	
	@Inject
	private SystemTime time;
	
	@Override
	@Transactional
	public void setDisplayName(int id, String displayName) {
		User user = getUser(id);
		if(user != null) {
			user.getUserDetails().setDisplayName(displayName);
		}
	}
	
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> listUserByStats(int limit, Field field, Set<Integer> userGroup) {
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
	@Transactional
	public User createUser(String extId, int operatorId) {
		User u = getUserByExternalId(extId, operatorId);
		if(u == null) {
			u = doCreateUser(extId, operatorId);
		}
		return u;
	}
	
	@Override
	@Transactional
	public User createLocalUser(String localName, String password) {
		User u = getUserByLocalName(localName);
		if(u == null) {
			u = doCreateLocalUser(localName, password);
		}
		return u;
	}

	@Override
	@Transactional
	public User getUser(int id) {
		return em.get().find(User.class, id);
	}

	@Override
	@Transactional
	public User getUserByExternalId(String extId, int operatorId) {
		return doGetUserByExternalId(extId, operatorId);
	}
	
	@Override
	@Transactional
	public User getUserByLocalName(String userName) {
		return doGetUserByLocalName(userName);
	}
	
	@Override
	@Transactional
	public User authLocalUser(String localName, String passwd) {
		return doAuthLocalUser(localName, passwd);
	}
	
	
	// --- PRIVATE METHODS --- //
	
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
	
	private User doGetUserByLocalName(String userName) {
		Query q = em.get().createQuery("select t from User t where t.localName = :localName");
		q.setParameter("localName", userName);
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	private User doCreateUser(String extId, int operatorId) {
		User s = newUser();
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
	
	private User doCreateLocalUser(String localName, String password) {
		User s = newUser();
		GameStats stats = new GameStats();
		stats.setEloRating(1500);
		em.get().persist(stats);
		UserDetails det = new UserDetails();
		det.setDisplayName(localName);
		em.get().persist(det);
		s.setGameStats(stats);
		s.setUserDetails(det);
		s.setOperatorId(0);
		s.setLocalName(localName);
		s.setLocalPassword(password);
		em.get().persist(s);
		return s;
	}

	private User newUser() {
		User s = new User();
		s.setStatus(LIVE);
		long t = time.utc();
		s.setCreated(t);
		s.setLastModified(t);
		return s;
	}
}
