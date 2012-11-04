package net.kalaha.web.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.Resolution;

public class NullResolution implements Resolution {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		resp.setStatus(200);
	}
}
