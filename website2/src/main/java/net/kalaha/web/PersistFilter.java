package net.kalaha.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

public class PersistFilter extends BaseGuiceFilter {

	@Inject
	private UnitOfWork unitOfWork;

	@Inject
	private PersistService persistService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
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
