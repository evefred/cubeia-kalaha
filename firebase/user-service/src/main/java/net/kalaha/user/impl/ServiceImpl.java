package net.kalaha.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.kalaha.common.guice.PropertiesModule;
import net.kalaha.data.manager.TransactionalManagerModule;
import net.kalaha.data.util.JpaInitializer;
import net.kalaha.user.api.UserService;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.login.LoginHandler;
import com.cubeia.firebase.api.login.LoginLocator;
import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.server.conf.ConfigProperty;
import com.cubeia.firebase.api.server.conf.PropertyKey;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.api.service.config.ClusterConfigProviderContract;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class ServiceImpl implements UserService, Service {

	private Injector injector;

	@Override
	public void init(ServiceContext context) throws SystemException {
		createInjector(context);
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
	
	private void createInjector(ServiceContext context) {
		ClusterConfigProviderContract serv = context.getParentRegistry().getServiceInstance(ClusterConfigProviderContract.class);
		List<Module> list = new ArrayList<Module>(5);
		// list.add(new FirebaseModule(context.getParentRegistry()));
		list.add(new JpaPersistModule("kalaha"));
		list.add(new ServiceModule());
		list.add(new TransactionalManagerModule());
		list.add(new PropertiesModule(getConfigProperties(serv)));
		injector = Guice.createInjector(list);
	}
	
	private Properties getConfigProperties(ClusterConfigProviderContract serv) {
		Properties p = new Properties();
		for (ConfigProperty prop : serv.getAllProperties()) {
			PropertyKey key = prop.getKey();
			String name = key.getNamespace() + "." + key.getProperty();
			p.put(name, prop.getValue());
		}
		return p;
	}
}