package net.kalaha.notice.impl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static net.kalaha.common.firebase.ConfigHelp.getClusterProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import net.kalaha.common.guice.PropertiesModule;
import net.kalaha.common.json.AbstractAction;
import net.kalaha.common.util.SafeRunnable;
import net.kalaha.common.util.Strings;
import net.kalaha.common.util.SystemTime;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.util.JpaInitializer;
import net.kalaha.notice.api.NextMoveNotice;
import net.kalaha.notice.api.NoticeManager;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.service.ServiceAction;
import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.RoutableService;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.ServiceRouter;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;

public class NoticeService implements Service, NoticeManager, RoutableService {

	private final Logger log = Logger.getLogger(getClass());
	
	@Inject
	private Transformer transformer;
	
	@Inject
	private Provider<NextMoveHandler> nextMove;
	
	@Inject
	@Named("facebook-app-id")
	private String appId;
	
	@Inject
	@Named("facebook-app-secret")
	private String appSecret;
	
	@Inject
	@SuppressWarnings("unused")
	private JpaInitializer jpaInit;
	
	@Inject
	private SystemTime time;
	
	private AtomicReference<String> accessToken = new AtomicReference<String>();
	
	private ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
	
	@Override
	public void destroy() { }

	@Override
	public void init(ServiceContext context) throws SystemException {
		List<Module> list = new ArrayList<Module>(5);
		// list.add(new FirebaseModule(context.getParentRegistry()));
		list.add(new JpaPersistModule("kalaha"));
		list.add(new ManagerModule());
		list.add(new PropertiesModule(getClusterProperties(context.getParentRegistry())));
		list.add(new ServiceModule(accessToken));
		Guice.createInjector(list).injectMembers(this);
		obtainFacebookAccessToken();
	}

	@Override
	public void start() { }

	@Override
	public void stop() { 
		exec.shutdown();
	}

	@Override
	public void setRouter(ServiceRouter router) { }

	@Override
	public void onAction(ServiceAction e) {
		String json = Strings.fromBytes(e.getData());
		log.debug("Incoming JSON: " + json);
		AbstractAction act = transformer.fromString(json);
		log.debug("Hand-off on action: " + act);
		exec.submit(new Task(act));
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void obtainFacebookAccessToken() {
		AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(appId, appSecret);
		log.info("Updating application access token to: " + accessToken.getAccessToken());
		this.accessToken.set(accessToken.getAccessToken());
		if(accessToken.getExpires() != null) {
			long ttl = accessToken.getExpires().getTime() - time.utc();
			log.info("Access token valid to " + accessToken.getExpires() + "; Scheduling new access");
			exec.schedule(new Runnable() {
				
				@Override
				public void run() {
					obtainFacebookAccessToken();
				}
			}, ttl, MILLISECONDS);
		} else {
			log.info("Access has no expiry date");
		}
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	private class Task extends SafeRunnable {
		
		private AbstractAction act;

		public Task(AbstractAction act) {
			this.act = act;
		}

		@Override
		public void execute() {
			if(act instanceof NextMoveNotice) {
				nextMove.get().handle((NextMoveNotice) act);
			} else {
				log.warn("Unknown action: " + act);
			}
		}
	}
}
