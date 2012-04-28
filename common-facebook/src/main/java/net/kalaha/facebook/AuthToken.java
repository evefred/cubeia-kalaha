package net.kalaha.facebook;

public class AuthToken {

	public final String token;
	public final long expires;
	
	AuthToken(String token, long expires) {
		this.token = token;
		this.expires = expires;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis() >= expires;
	}
}
