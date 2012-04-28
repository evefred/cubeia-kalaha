package net.kalaha.data.manager;

import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import net.kalaha.entities.Session;
import net.kalaha.entities.User;
import net.kalaha.entities.UserDetails;

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
	public void setDisplayName(int id, String displayName) {
		trans.enter();
		try {
			User user = getUser(id);
			if(user != null) {
				user.getUserDetails().setDisplayName(displayName);
			}
			trans.commit();
		} catch(Exception e) {
			log.error("Failed transaction", e);
			trans.rollback();
		} finally {
			trans.exit();
		}
	}
	
	@Override
	public Session getSessionByUserId(int userId) {
		trans.enter();
		try {
			Session s = doGetSessionByUserId(userId);
			if(s != null) return s;
			User u = getUser(userId);
			if(u == null) return null;
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
	public User createLocalUser(String localName, String password) {
		trans.enter();
		try {
			User u = getUserByLocalName(localName);
			if(u == null) u = doCreateLocalUser(localName, password);
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
	public User getUserByLocalName(String userName) {
		trans.enter();
		try {
			return doGetUserByLocalName(userName);
		} finally {
			trans.exit();
		}
	}
	
	@Override
	public User authLocalUser(String localName, String passwd) {
		trans.enter();
		try {
			return doAuthLocalUser(localName, passwd);
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
	
	private User doAuthLocalUser(String localName, String passwd) {
		Query q = trans.current().createQuery("select t from User t where t.localName = :localName and t.localPassword = :localPassword");
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
	
	private User doGetUserByLocalName(String userName) {
		Query q = trans.current().createQuery("select t from User t where t.localName = :localName");
		q.setParameter("localName", userName);
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
		UserDetails det = new UserDetails();
		trans.current().persist(det);
		s.setUserDetails(det);
		s.setExternalId(extId);
		s.setOperatorId(operatorId);
		trans.current().persist(s);
		return s;
	}
	
	private User doCreateLocalUser(String localName, String password) {
		User s = new User();
		UserDetails det = new UserDetails();
		det.setDisplayName(localName);
		trans.current().persist(det);
		s.setUserDetails(det);
		s.setOperatorId(0);
		s.setLocalName(localName);
		s.setLocalPassword(password);
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
	
	private Session doGetSessionByUserId(int userId) {
		Query q = trans.current().createQuery("select t from Session t where t.userId = :userId");
		q.setParameter("userId", userId);
		q.setMaxResults(1);
		try {
			return (Session) q.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
}
