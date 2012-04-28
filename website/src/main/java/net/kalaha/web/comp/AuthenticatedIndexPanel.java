package net.kalaha.web.comp;

import java.util.List;

import net.kalaha.data.manager.GameManager;
import net.kalaha.entities.Game;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.User;
import net.kalaha.web.BasePanel;
import net.kalaha.web.Challenge;
import net.kalaha.web.FacebookChallenge;
import net.kalaha.web.FacebookPlay;
import net.kalaha.web.Play;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

public class AuthenticatedIndexPanel extends BasePanel {

	private static final long serialVersionUID = -2970319053688722648L;
	private final GameManager gameManager;
	private final Class<? extends Page> challengePage;
	private final Class<? extends Page> playPage;

	public AuthenticatedIndexPanel(String id, GameManager gameManager, boolean isFacebook) {
		super(id);
		this.gameManager = gameManager;
		if(isFacebook) {
			this.challengePage = FacebookChallenge.class;
			this.playPage = FacebookPlay.class;
		} else {
			this.challengePage = Challenge.class;
			this.playPage = Play.class;
		}
		setup();
	}
	
	public AuthenticatedIndexPanel(String id, GameManager gameManager) {
		this(id, gameManager, false);
	}

	private void setup() { 
		add(new BookmarkablePageLink<Void>("challenge-link", challengePage));
		List<Game> myGames = gameManager.getMyGames(getCurrentUser(), GameStatus.ACTIVE, null);
		ListView<Game> view = new ListView<Game>("gameView", myGames) {
			
			private static final long serialVersionUID = 7816560728323554003L;

			@Override
			protected void populateItem(ListItem<Game> item) {
				Game game = item.getModelObject();
				item.add(new AttributeModifier("id", true, Model.of("gameRow" + game.getId())));
				User opp = game.getMyOpponent(getCurrentUser());
				item.add(new Label("opponent", opp.getUserDetails().getDisplayName()));
				item.add(new Label("created", formatDate(game.getCreated())));
				// WebMarkupContainer link = new WebMarkupContainer("gameLink");
				// link.add(new AttributeModifier("onclick", true, Model.of("openGame(" + game.getId() + ")")));
				// link.add(new AttributeModifier("id", true, Model.of("gameRowLink" + game.getId())));
				BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("gameLink", playPage, new PageParameters("gameId=" + game.getId()));
				link.add(new Label("state", game.isMyTurn(opp) ? "Waiting... " : "My Turn!"));
				item.add(link);
				item.add(new Label("lastMove", formatDate(game.getLastModified())));
				item.add(new Label("noMoves", String.valueOf(game.getStates().size())));
			}
		};
		add(view);
	}
}
