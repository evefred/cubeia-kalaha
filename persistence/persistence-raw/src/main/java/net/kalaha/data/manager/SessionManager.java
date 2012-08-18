package net.kalaha.data.manager;

import java.util.List;

import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;

public interface SessionManager {
	
	public long countSessions();
	
	public List<Session> reapSessions(long maxAge);

	public Session getSessionByExternalId(String extId, int operatorId);
	
	public Session getSessionByUserId(long id);

	public User getUserBySession(String sessionId);
		
	public Session getSessionById(String id);

}
