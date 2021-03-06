package net.kalaha.data.manager;

import java.util.List;
import java.util.Set;

import net.kalaha.data.entities.GameStats;
import net.kalaha.data.entities.User;
import net.kalaha.data.entities.UserStatus;

public interface UserManager {

	public User getUser(long id);
	
	public User getUserByExternalId(String extId, int operatorId);

	public User createUser(String extId, int operatorId, UserStatus status);

	public User createLocalUser(String localName, String paswd);
	
	public User authLocalUser(String localName, String passwd);
	
	public void setDisplayName(long id, String displayName);

	public User getUserByLocalName(String name);
	
	public List<User> listUserByStats(int limit, GameStats.Field field, Set<Long> userGroup);

	public User authBot(String userName, int botId);
	
}
