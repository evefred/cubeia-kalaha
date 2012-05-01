package net.kalaha.web.comp;

import net.kalaha.data.manager.GameManager;
import net.kalaha.web.BasePanel;
import net.kalaha.web.Challenge;
import net.kalaha.web.Play;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;

public class AuthenticatedIndexPanel extends BasePanel {

	private static final long serialVersionUID = -2970319053688722648L;
	private final GameManager gameManager;

	public AuthenticatedIndexPanel(String id, GameManager gameManager) {
		super(id);
		this.gameManager = gameManager;
		setup();
	}

	private void setup() { 
		add(new BookmarkablePageLink<Void>("challenge-link", Challenge.class));
		add(new GameTablePanel("gameTable", gameManager, Play.class));
	}
}
