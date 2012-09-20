package net.kalaha.session.impl;

import java.util.Properties;

import net.kalaha.common.guice.PropertiesModule;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.util.JpaInitializer;
import net.kalaha.session.api.SessionManager;

import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.server.conf.ConfigProperty;
import com.cubeia.firebase.api.server.conf.PropertyKey;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.config.ClusterConfigProviderContract;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;

public class SessionManagerImpl implements Service, SessionManager {
	
	protected Injector injector;
	protected ServiceContext con;
	
	@Inject
	private SessionReaper reaper;

	public void init(ServiceContext con) throws SystemException { 
		this.con = con;
		createInjector();
	}

	public void start() {
		injector.getInstance(JpaInitializer.class);
		reaper.start();
	}

	public void stop() {
		reaper.stop();
	}
	
	public void destroy() { }
	

	// --- PROTECTED METHODS --- //
	
	protected void createInjector() {
		ClusterConfigProviderContract serv = con.getParentRegistry().getServiceInstance(ClusterConfigProviderContract.class);
		injector = Guice.createInjector(new ManagerModule(), new JpaPersistModule("kalaha"), new PropertiesModule(getConfigProperties(serv)));
		injector.injectMembers(this);
	}

	protected Properties getConfigProperties(ClusterConfigProviderContract serv) {
		Properties p = new Properties();
		for (ConfigProperty prop : serv.getAllProperties()) {
			PropertyKey key = prop.getKey();
			String name = key.getNamespace() + "." + key.getProperty();
			p.put(name, prop.getValue());
		}
		return p;
	}
}