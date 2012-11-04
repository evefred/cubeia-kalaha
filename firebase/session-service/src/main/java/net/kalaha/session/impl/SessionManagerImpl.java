package net.kalaha.session.impl;

import static net.kalaha.common.firebase.ConfigHelp.getClusterProperties;
import net.kalaha.common.guice.PropertiesModule;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.data.util.JpaInitializer;
import net.kalaha.session.api.SessionManager;

import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
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
		injector = Guice.createInjector(
						new ManagerModule(), 
						new JpaPersistModule("kalaha"), 
						new PropertiesModule(getClusterProperties(con.getParentRegistry())));
		injector.injectMembers(this);
	}
}