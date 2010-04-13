package net.kalaha.facebook.page;

import java.util.List;

import net.kalaha.entities.Game;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.User;
import net.kalaha.facebook.BasePage;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

public class Index extends BasePage {

	public Index(PageParameters parameters) {
		super(parameters);
		setup();
	}

	
	
	// --- PRIVATE METHODS --- //

	private void setup() {
		setupGameTable();
		setupInviteLink();
	}	
	
	private void setupInviteLink() {
		add(new BookmarkablePageLink<Void>("inviteLink", Invite.class));
	}

	private void setupGameTable() {
		List<Game> myGames = gameManager.getMyGames(getCurrentUser(), GameStatus.ACTIVE, null);
		add(new ListView<Game>("gameView", myGames) {
			
			private static final long serialVersionUID = 7816560728323554003L;

			@Override
			protected void populateItem(ListItem<Game> item) {
				Game game = item.getModelObject();
				User opp = game.getMyOpponent(getCurrentUser());
				Index.this.addFBAttribute(item, "uid", "uid", opp.getExternalId());
				item.add(new Label("created", formatDate(game.getCreated())));
				item.add(new Label("state", (game.isMyTurn(opp) ? "Waiting... " : "My Turn!")));
				item.add(new Label("lastMove", formatDate(game.getLastModified())));
				item.add(new Label("noMoves", String.valueOf(game.getStates().size())));
			}
		});
	}
}
