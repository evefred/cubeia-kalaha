package net.kalaha.web.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import net.kalaha.common.util.SafeRunnable;
import net.kalaha.data.entities.User;
import net.kalaha.web.action.FacebookUser;

public class IndexerEventSink implements EventSink {
	
	@Inject(optional=true)
	@Named("indexer-url")
	private String indexUrl;

	private ExecutorService exec = Executors.newFixedThreadPool(5);
	
	@Override
	public void userLoggedIn(User user, FacebookUser fbuser) {
		if(isConfigured()) {
			index(fbuser);
		}
	}
	
	
	// --- PRIVATE METHODS --- //

	private void index(final FacebookUser fbuser) {
		exec.submit(new SafeRunnable() {
			
			@Override
			protected void execute() {
				
			}
		});
	}

	private boolean isConfigured() {
		return indexUrl != null && indexUrl.length() > 0;
	}
}
