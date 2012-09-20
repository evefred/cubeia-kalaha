package net.kalaha.data.manager;

import net.kalaha.data.entities.Request;
import net.kalaha.data.entities.RequestStatus;
import net.kalaha.data.entities.User;

public interface RequestManager {
	
	public Request getRequestByExternalId(String extId);

	public Request updateRequest(long id, RequestStatus status);
	
	public Request updateRequestByExternalId(String extId, RequestStatus status);

	public Request invite(User inviter, String extInvitedId, int operatorId, String extId);

	public Request challenge(User challenger, User challengee, String extId);

}
