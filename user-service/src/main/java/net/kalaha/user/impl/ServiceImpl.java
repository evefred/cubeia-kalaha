package net.kalaha.user.impl;

import java.util.ArrayList;
import java.util.List;

import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.util.JpaInitializer;
import net.kalaha.user.api.UserService;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class ServiceImpl implements UserService, Service {

	private Injector injector;

	@Override
	public void init(ServiceContext context) throws SystemException {
		createInjector();
	}
	
	@Override
	public void destroy() { }
	
	@Override
	public void stop() { }

	@Override
	public void init(ServiceRegistry arg0) { }
	
	@Override
	public void start() { 
		injector.getInstance(JpaInitializer.class);
	}
	
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
		// list.add(new FirebaseModule(context.getParentRegistry()));
		list.add(new JpaPersistModule("kalaha"));
		list.add(new ServiceModule());
		list.add(new ManagerModule());
		injector = Guice.createInjector(list);
	}
}