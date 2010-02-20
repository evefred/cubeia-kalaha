package net.kalaha.user.api;

public interface UserManager {

	public User getUser(int id);
	
	public User getUserByExternalId(String extId, int operatorId);

	public Session getSessionByExternalId(String extId, int operatorId);
	
	public User getUserBySession(String sessionId);
	
	public Session getSessionById(String id);
	
}
