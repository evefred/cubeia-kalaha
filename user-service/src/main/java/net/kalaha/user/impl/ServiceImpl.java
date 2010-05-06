package net.kalaha.user.impl;

import java.util.List;

import net.kalaha.data.manager.ManagerModule;
import net.kalaha.user.api.UserService;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.guice.service.GuiceService;
import com.google.inject.Module;

public class ServiceImpl extends GuiceService implements UserService {

	@Override
	public void init(ServiceRegistry reg) { }
	
	@Override
	public void init(ServiceContext context) throws SystemException {
		super.init(context);
	}
	
	@Override
	public LoginHandler locateLoginHandler(LoginRequestAction req) {
		return guice(LoginLocator.class).locateLoginHandler(req);
	}
	
	/*@Override
	public UserManager getUserManager() {
		return guice(UserManager.class);
	}*/
	
	
	// --- CONFIGURATION --- //
	
	@Override
	protected void preInjectorCreation(List<Module> modules) {
		modules.add(new ServiceModule(context.getParentRegistry(), true));
		modules.add(new ManagerModule());
	}
}