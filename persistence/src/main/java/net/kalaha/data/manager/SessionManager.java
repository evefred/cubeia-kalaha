package net.kalaha.data.manager;

import net.kalaha.data.entities.Session;
import net.kalaha.data.entities.User;

public interface SessionManager {

	public Session getSessionByExternalId(String extId, int operatorId);
	
	public Session getSessionByUserId(int id);

	public User getUserBySession(String sessionId);
		
	public Session getSessionById(String id);

}
