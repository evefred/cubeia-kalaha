package net.kalaha.user.impl;

import net.kalaha.data.manager.UserManager;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.action.service.ServiceAction;
import com.cubeia.firebase.api.service.RoutableService;
import com.cubeia.firebase.api.service.ServiceRouter;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RoutableServiceImpl implements RoutableService {

	@Inject
	@SuppressWarnings("unused")
	private UserManager manager;
	
	private final Logger log = Logger.getLogger(getClass());

	@SuppressWarnings("unused")
	private ServiceRouter router;
	
	@Override
	public void onAction(ServiceAction arg0) {
		log.debug("On action: " + arg0);
	}

	@Override
	public void setRouter(ServiceRouter router) {
		this.router = router;
	}
}
