package net.kalaha.data.manager;

import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.kalaha.entities.Session;
import net.kalaha.entities.User;

import org.apache.log4j.Logger;

import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserManagerImpl implements UserManager {
	
	@Inject
	private Transactions trans;

	@Log4j
	private Logger log;
	
	@Override
	public Session getSessionByExternalId(String extId, int operatorId) {
		trans.enter();
		try {
			Session s = doGetSessionByExternalId(extId, operatorId);
			if(s != null) return s;
			User u = getUserByExternalId(extId, operatorId);
			if(u == null) u = createUser(extId, operatorId);
			s = doCreateNewSession(u);
			trans.commit();
			return s;
		} catch(Exception e) {
			log.error("Failed transaction", e);
			trans.rollback();
			return null;
		} finally {
			trans.exit();
		}
	}
	
	@Override
	public User createUser(String extId, int operatorId) {
		trans.enter();
		try {
			User u = getUserByExternalId(extId, operatorId);
			if(u == null) u = doCreateUser(extId, operatorId);
			trans.commit();
			return u;
		} catch(Exception e) {
			log.error("Failed transaction", e);
			trans.rollback();
			return null;
		} finally {
			trans.exit();
		}
	}

	@Override
	public Session getSessionById(String id) {
		trans.enter();
		try {
			return doGetSessionById(id);
		} finally {
			trans.exit();
		}
	}

	@Override
	public User getUser(int id) {
		trans.enter();
		try {
			return trans.current().find(User.class, id);
		} finally {
			trans.exit();
		}
	}

	@Override
	public User getUserByExternalId(String extId, int operatorId) {
		trans.enter();
		try {
			return doGetUserByExternalId(extId, operatorId);
		} finally {
			trans.exit();
		}
	}

	@Override
	public User getUserBySession(String id) {
		trans.enter();
		try {
			Session s = getSessionById(id);
			if(s == null) return null;
			Query q = trans.current().createQuery("select s from User s where s.id = :id");
			q.setParameter("id", s.getUserId());
			q.setMaxResults(1);
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		} finally {
			trans.exit();
		}
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private Session doGetSessionById(String id) {
		return trans.current().find(Session.class, id);
	}
	
	private User doGetUserByExternalId(String extId, int operatorId) {
		Query q = trans.current().createQuery("select t from User t where t.externalId = :extId and t.operatorId = :opId");
		q.setParameter("extId", extId);
		q.setParameter("opId", operatorId);
		q.setMaxResults(1);
		try {
			return (User) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	private Session doCreateNewSession(User u) {
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
		trans.current().persist(s);
		return s;
	}

	private User doCreateUser(String extId, int operatorId) {
		User s = new User();
		s.setExternalId(extId);
		s.setOperatorId(operatorId);
		trans.current().persist(s);
		return s;
	}

	private Session doGetSessionByExternalId(String extId, int operatorId) {
		Query q = trans.current().createQuery("select t from Session t where t.externalId = :extId and t.operatorId = :opId");
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
