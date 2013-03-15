package net.kalaha.web.action;

import com.restfb.types.User;

public class FacebookUser extends User {

	private static final long serialVersionUID = -4540124196871558391L; 

	private long kalahaId;
	private String kalahDisplayName;
	
	public long getKalahaId() {
		return kalahaId;
	}
	
	public String getKalahDisplayName() {
		return kalahDisplayName;
	}
	
	public void setKalahaId(long kalahaId) {
		this.kalahaId = kalahaId;
	}
	
	public void setKalahDisplayName(String kalahDisplayName) {
		this.kalahDisplayName = kalahDisplayName;
	}
	
    public void updateKalahDetails(net.kalaha.data.entities.User user) {
    	this.kalahDisplayName = user.getUserDetails().getDisplayName();
    	this.kalahaId = user.getId();
    }
}
