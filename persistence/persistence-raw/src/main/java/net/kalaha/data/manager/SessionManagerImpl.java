package net.kalaha.data.manager;

import static net.kalaha.data.entities.UserStatus.LIVE;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
@Transactional
public class SessionManagerImpl implements SessionManager {
	
	@Inject
	private Provider<EntityManager> em;
	
	@Inject
	private UserManager users;
	
	@Inject
	private SystemTime time;
	
	@Override
	public long countSessions() {
		Query q = em.get().createQuery("select count(s.id) from Session s");
		try {
			Long i = (Long) q.getSingleResult();
			return i.longValue();
		} catch(NoResultException e) {
			return 0;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Session> reapSessions(long maxAge) {
		long last = time.utc() - maxAge;
		Query q = em.get().createQuery("select s from Session s where s.lastModified < :last");
		q.setParameter("last", last);
		List<Session> sessions = (List<Session>) q.getResultList();
		for (Session s : sessions) {
			em.get().remove(s);
		}
		return sessions;
	}
	
	@Override
	public Session getSessionByExternalId(String extId, int operatorId) {
		Session s = doGetSessionByExternalId(extId, operatorId);
		if(s != null) {
			return s;
		}
		User u = users.getUserByExternalId(extId, operatorId);
		if(u == null) {
			u = users.createUser(extId, operatorId, LIVE);
		}
		s = doCreateNewSession(u);
		return s;
	}
	
	@Override
	public Session getSessionByUserId(long userId) {
		Session s = doGetSessionByUserId(userId);
		if(s != null) {
			return s;
		}
		User u = users.getUser(userId);
		if(u == null) {
			return null;
		}
		s = doCreateNewSession(u);
		return s;
	}
	
	@Override
	public Session getSessionById(String id) {
		return doGetSessionById(id);
	}
	
	@Override
	public User getUserBySession(String id) {
		Session s = getSessionById(id);
		if(s == null) {
			return null;
		}
		Query q = em.get().createQuery("select s from User s where s.id = :id");
		q.setParameter("id", s.getUserId());
		q.setMaxResults(1);
		return (User) q.getSingleResult();
	}
	
	
	
	// --- PRIVATE METHODS --- //
	
	private Session doGetSessionById(String id) {
		return em.get().find(Session.class, id);
	}
	
	private Session doCreateNewSession(User u) {
		Session s = new Session();
		UUID uid = UUID.randomUUID();
		s.setId(uid.toString());
		s.setUserId(u.getId());
		long t = time.utc();
		s.setCreated(t);
		s.setOperatorId(u.getOperatorId());
		s.setExternalId(u.getExternalId());
		s.setLastModified(t);
		s.setTtl(-1);
		em.get().persist(s);
		return s;
	}
	
	private Session doGetSessionByExternalId(String extId, int operatorId) {
		Query q = em.get().createQuery("select t from Session t where t.externalId = :extId and t.operatorId = :opId");
		q.setParameter("extId", extId);
		q.setParameter("opId", operatorId);
		q.setMaxResults(1);
		try {
			return (Session) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	private Session doGetSessionByUserId(long userId) {
		Query q = em.get().createQuery("select t from Session t where t.userId = :userId");
		q.setParameter("userId", userId);
		q.setMaxResults(1);
		try {
			return (Session) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}
