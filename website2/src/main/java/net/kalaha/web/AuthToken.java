package net.kalaha.web;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AuthToken {

	private final String token;
	private final long expires;
	
	AuthToken(String token, long expires) {
		this.token = token;
		this.expires = expires;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis() >= expires;
	}
	
	public long getExpires() {
		return expires;
	}
	
	public String getToken() {
		return token;
	}
	
	
	// --- COMMON METHODS --- //

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
