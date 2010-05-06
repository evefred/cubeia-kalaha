package net.kalaha.facebook.page.fb;

import java.io.Serializable;

import com.restfb.Facebook;

public class FBUser implements Serializable {

	private static final long serialVersionUID = 3366811209840056453L;
	
	@Facebook
	public String name;
	
	@Facebook
	public Long uid;
	
}
