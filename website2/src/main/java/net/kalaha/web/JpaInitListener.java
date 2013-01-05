package net.kalaha.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.kalaha.data.util.JpaInitializer;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class JpaInitListener implements ServletContextListener {

	@Inject
	@SuppressWarnings("unused")
	private JpaInitializer jpaInit;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Injector injector = (Injector) sce.getServletContext().getAttribute(Injector.class.getName());
		injector.injectMembers(this);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) { }

}
