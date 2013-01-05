package net.kalaha.game.impl;

import static net.kalaha.common.firebase.ConfigHelp.getClusterProperties;

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import net.kalaha.common.guice.PropertiesModule;
import net.kalaha.data.manager.ManagerModule;
import net.kalaha.game.api.GameService;

import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class GameServiceImpl implements Service, GameService {
	
	private Injector injector;

	@Override
	public void init(ServiceContext context) throws SystemException {
		createInjector(context);
		mountGameCreator(context);
	}

	public void start() { }

	public void stop() {}
	
	public void destroy() {}

	// --- PRIVATE METHODS --- //
	
	private void mountGameCreator(ServiceContext context) throws SystemException {
		MBeanServer mbs = context.getMBeanServer();
		try {
			ObjectName name = new ObjectName("net.kalaha.game:type=GameCreator");
			if(!mbs.isRegistered(name)) {
				mbs.registerMBean(injector.getInstance(GameCreator.class), name);
			}
		} catch(Exception e) {
			throw new SystemException("Failed to mount JMX bean", e);
		}
	}
	
	private void createInjector(ServiceContext context) {
		List<Module> list = new ArrayList<Module>(5);
		// list.add(new FirebaseModule(context.getParentRegistry()));
		list.add(new JpaPersistModule("kalaha"));
		list.add(new ManagerModule());
		list.add(new PropertiesModule(getClusterProperties(context.getParentRegistry())));
		injector = Guice.createInjector(list);
	}
}