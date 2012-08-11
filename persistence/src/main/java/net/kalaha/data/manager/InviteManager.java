package net.kalaha.data.manager;

import net.kalaha.data.entities.Invite;
import net.kalaha.data.entities.InviteStatus;
import net.kalaha.data.entities.User;

public interface InviteManager {

	public Invite updateInvite(int id, InviteStatus status);
	
	public Invite invite(User inviter, String extInvitedId);

}
