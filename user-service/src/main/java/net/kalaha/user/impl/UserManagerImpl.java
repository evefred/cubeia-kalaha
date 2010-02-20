package net.kalaha.user.impl;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import net.kalaha.user.api.UserManager;
import net.kalaha.user.api.Session;
import net.kalaha.user.api.User;

import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UserManagerImpl implements UserManager {
	
	@Inject
	private Provider<EntityManager> manager;

	@Log4j
	private Logger log;
	
	@Override
	public Session getSessionByExternalId(String extId, int operatorId) {
		EntityManager em = manager.get();
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		try {
			Session s = getSessionByExternalId(em, extId, operatorId);
			if(s != null) return s;
			User u = getUserByExternalId(em, extId, operatorId);
			if(u == null) u = createNewUser(em, extId, operatorId);
			s = createNewSession(em, u);
			tr.commit();
			return s;
		} catch(Exception e) {
			log.error("Failed transaction", e);
			tr.rollback();
			return null;
		} finally {
			em.close();
		}
	}

	@Override
	public Session getSessionById(String id) {
		EntityManager em = manager.get();
		Session u = getSessionById(em, id);
		em.close();
		return u;
	}

	@Override
	public User getUser(int id) {
		EntityManager em = manager.get();
		User u = em.find(User.class, id);
		em.close();
		return u;
	}

	@Override
	public User getUserByExternalId(String extId, int operatorId) {
		EntityManager em = manager.get();
		User u = getUserByExternalId(em, extId, operatorId);
		em.close();
		return u;
	}

	@Override
	public User getUserBySession(String id) {
		EntityManager em = manager.get();
		Session s = getSessionById(em, id);
		Query q = em.createQuery("select s from User s where s.id = :id");
		q.setParameter("id", s.getUserId());
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private Session getSessionById(EntityManager em, String id) {
		return em.find(Session.class, id);
	}
	
	private User getUserByExternalId(EntityManager em, String extId, int operatorId) {
		Query q = em.createQuery("select t from User t where t.externalId = :extId and t.operatorId = :opId");
		q.setParameter("extId", extId);
		q.setParameter("opId", operatorId);
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	private Session createNewSession(EntityManager em, User u) {
		Session s = new Session();
		UUID uid = UUID.randomUUID();
		s.setId(uid.toString());
		s.setUserId(u.getId());
		long t = System.currentTimeMillis();
		s.setCreated(t);
		s.setOperatorId(u.getOperatorId());
		s.setExternalId(u.getExternalId());
		s.setLastModified(t);
		s.setTtl(-1);
		em.persist(s);
		return s;
	}

	private User createNewUser(EntityManager em, String extId, int operatorId) {
		User s = new User();
		s.setExternalId(extId);
		s.setOperatorId(operatorId);
		em.persist(s);
		return s;
	}

	private Session getSessionByExternalId(EntityManager em, String extId, int operatorId) {
		Query q = em.createQuery("select t from Session t where t.externalId = :extId and t.operatorId = :opId");
		q.setParameter("extId", extId);
		q.setParameter("opId", operatorId);
		q.setMaxResults(1);
		try {
			return (Session) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}
