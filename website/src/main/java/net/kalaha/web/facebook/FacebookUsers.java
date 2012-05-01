package net.kalaha.web.facebook;

import java.util.concurrent.ExecutionException;


import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.inject.Singleton;
import com.restfb.FacebookClient;

@Singleton
public class FacebookUsers {

	private Logger log = Logger.getLogger(getClass());
	
	private final LoadingCache<String, FacebookUser> cache;
	private FacebookClient client = null;
	
	public FacebookUsers() {
		cache = CacheBuilder.newBuilder().build(new CacheLoader<String, FacebookUser>() {
			
			@Override
			public FacebookUser load(String key) throws Exception {
				return client.fetchObject(key, FacebookUser.class);
			}
		});
	}
	
	void setClient(FacebookClient client) {
		this.client = client;
	}
	
	public FacebookUser get(String id) {
		try {
			return cache.get(id);
		} catch (ExecutionException e) {
			log.error("Failed to get user from Facebook", e);
			return null;
		}
	}
}
