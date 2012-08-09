package net.kalaha.web.comp;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.User;
import net.kalaha.web.BasePanel;

public class GameTablePanel extends BasePanel {

	private static final long serialVersionUID = 6129006600253941998L;
	
	private final GameManager gameManager;
	private final Class<? extends Page> playPage;

	public GameTablePanel(String id, GameManager gameManager, Class<? extends Page> playPage) {
		super(id);
		this.gameManager = gameManager;
		this.playPage = playPage;
		setup();
	}
	
	
	// --- PRIVATE METHODS --- //
	
	private void setup() { 
		List<Game> myGames = gameManager.getMyGames(getCurrentUser(), GameStatus.ACTIVE);
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
