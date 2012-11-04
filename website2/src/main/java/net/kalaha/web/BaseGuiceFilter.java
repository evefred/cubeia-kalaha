package net.kalaha.web;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.google.inject.Injector;

public abstract class BaseGuiceFilter implements Filter {

	protected final Logger log = Logger.getLogger(getClass());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Injector injector = (Injector) filterConfig.getServletContext().getAttribute(Injector.class.getName());
		injector.injectMembers(this);
	}
	
	@Override
	public void destroy() { }
}
