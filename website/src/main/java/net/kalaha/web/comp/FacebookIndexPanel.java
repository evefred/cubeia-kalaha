package net.kalaha.web.comp;

import net.kalaha.data.manager.GameManager;
import net.kalaha.web.BasePanel;
import net.kalaha.web.FacebookPlay;

public class FacebookIndexPanel extends BasePanel {

	private static final long serialVersionUID = -2970319053688722648L;
	private final GameManager gameManager;

	public FacebookIndexPanel(String id, GameManager gameManager) {
		super(id);
		this.gameManager = gameManager;
		setup();
	}

	private void setup() { 
		add(new GameTablePanel("gameTable", gameManager, FacebookPlay.class));
	}
}
