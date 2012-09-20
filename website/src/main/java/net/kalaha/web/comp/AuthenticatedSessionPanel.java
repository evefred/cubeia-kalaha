package net.kalaha.web.comp;

import net.kalaha.web.BasePanel;
import net.kalaha.web.KalahaSession;

import org.apache.wicket.markup.html.basic.Label;

public class AuthenticatedSessionPanel extends BasePanel {

	private static final long serialVersionUID = -2970319053688722648L;

	public AuthenticatedSessionPanel(String id) {
		super(id);
		setup();
	}

	private void setup() { 
		KalahaSession ses = getKalahaSession();
		add(new Label("userName", ses.getDisplayName()));
	}
}
