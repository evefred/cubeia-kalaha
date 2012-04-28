package net.kalaha.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.kalaha.entities.User;

import org.apache.wicket.markup.html.panel.Panel;

public class BasePanel extends Panel {

	private static final long serialVersionUID = -7212877368010187287L;
	
	public BasePanel(String id) {
		super(id);
	}

	protected User getCurrentUser() {
		return getKalahaSession().getUser();
	}
	
	protected String formatDate(Date date) {
		return new SimpleDateFormat("EEE, d MMM yyyy").format(date);
	}
	
	protected String formatDate(long date) {
		return formatDate(new Date(date));
	}
	
	public KalahaSession getKalahaSession() {
		return (KalahaSession) getSession();
	}
}
