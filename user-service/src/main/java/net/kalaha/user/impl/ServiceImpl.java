package net.kalaha.user.impl;

import java.util.ArrayList;
import java.util.List;

import net.kalaha.data.manager.ManagerModule;
import net.kalaha.user.api.UserService;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.guice.inject.FirebaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class ServiceImpl implements UserService, Service {

	private static final boolean ALLOW_TRIVIAL_LOGIN = false;
	
	private Injector injector;
	private ServiceContext context;

	@Override
	public void init(ServiceContext context) throws SystemException {
		this.context = context;
		createInjector();
	}
	
	@Override
	public void destroy() { }
	
	@Override
	public void stop() { }

	@Override
	public void init(ServiceRegistry arg0) { }
	
	@Override
	public void start() { }
	
	@Override
	public LoginHandler locateLoginHandler(LoginRequestAction req) {
		return injector.getInstance(LoginLocator.class).locateLoginHandler(req);
	}
	
	/*@Override
	public UserManager getUserManager() {
		return guice(UserManager.class);
	}*/
	
	
	// --- CONFIGURATION --- //
	
	private void createInjector() {
		List<Module> list = new ArrayList<Module>(5);
		list.add(new FirebaseModule(context.getParentRegistry()));
		list.add(new ServiceModule(context.getParentRegistry(), ALLOW_TRIVIAL_LOGIN));
		list.add(new ManagerModule());
		injector = Guice.createInjector(list);
	}
}