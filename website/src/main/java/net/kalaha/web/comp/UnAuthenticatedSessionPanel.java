package net.kalaha.web.comp;

import net.kalaha.web.Login;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

public class UnAuthenticatedSessionPanel extends Panel {

	private static final long serialVersionUID = -2970319053688722648L;

	public UnAuthenticatedSessionPanel(String id) {
		super(id);
		setup();
	} 

	private void setup() { 
		add(new BookmarkablePageLink<Void>("site-login", Login.class));
		add(new BookmarkablePageLink<Void>("facebook-login", Login.class, new PageParameters("facebook-login=true")));
	}
}
