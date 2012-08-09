package net.kalaha.data.manager;

import net.kalaha.data.entities.User;

public interface UserManager {

	public User getUser(int id);
	
	public User getUserByExternalId(String extId, int operatorId);

	public User createUser(String extId, int operatorId);

	public User createLocalUser(String localName, String paswd);
	
	public User authLocalUser(String localName, String passwd);
	
	public void setDisplayName(int id, String displayName);

	public User getUserByLocalName(String name);
	
}
