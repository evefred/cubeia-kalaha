package net.kalaha.data.manager;

import net.kalaha.entities.Session;
import net.kalaha.entities.User;

public interface UserManager {

	public User getUser(int id);
	
	public User getUserByExternalId(String extId, int operatorId);

	public Session getSessionByExternalId(String extId, int operatorId);
	
	public Session getSessionByUserId(int id);

	public User createUser(String extId, int operatorId);

	public User createLocalUser(String localName, String paswd);
	
	public User authLocalUser(String localName, String passwd);
	
	public User getUserBySession(String sessionId);
	
	public void setDisplayName(int id, String displayName);
	
	public Session getSessionById(String id);

	public User getUserByLocalName(String name);
	
}
