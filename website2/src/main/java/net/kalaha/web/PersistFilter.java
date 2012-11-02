package net.kalaha.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

public class PersistFilter implements Filter {

	@Inject
	private UnitOfWork unitOfWork;

	@Inject
	private PersistService persistService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Injector injector = (Injector) filterConfig.getServletContext().getAttribute(Injector.class.getName());
		injector.injectMembers(this);
		persistService.start();
	}

	public void destroy() {
		persistService.stop();
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		unitOfWork.begin();
		try {
			filterChain.doFilter(servletRequest, servletResponse);
		} finally {
			unitOfWork.end();
		}
	}
}
