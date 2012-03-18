package net.kalaha.facebook;

import java.util.List;

import net.kalaha.entities.Game;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.User;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

public class Index extends BasePage {
	
	@Inject
	private FacebookUsers users;

	public Index(PageParameters parameters) {
		super(parameters);
		setup();
	}

	
	
	// --- PRIVATE METHODS --- //

	private void setup() {
		if(getSession().getSession() == null) return; // NOT AUTH
		setupGameTable();
		// setupInviteLink();
	}	
	
	/*private void setupInviteLink() {
		add(new BookmarkablePageLink<Void>("inviteLink", Invite.class));
	}*/

	private void setupGameTable() {
		List<Game> myGames = gameManager.getMyGames(getCurrentUser(), GameStatus.ACTIVE, null);
		ListView<Game> view = new ListView<Game>("gameView", myGames) {
			
			private static final long serialVersionUID = 7816560728323554003L;

			@Override
			protected void populateItem(ListItem<Game> item) {
				Game game = item.getModelObject();
				item.add(new AttributeModifier("id", true, Model.of("gameRow" + game.getId())));
				User opp = game.getMyOpponent(getCurrentUser());
				if(opp.getLocalName() != null) {
					item.add(new Label("opponent", opp.getLocalName()));
				} else {
					item.add(new Label("opponent", users.get(opp.getExternalId()).getName()));
				}
				item.add(new Label("created", formatDate(game.getCreated())));
				WebMarkupContainer link = new WebMarkupContainer("gameLink");
				link.add(new AttributeModifier("onclick", true, Model.of("openGame(" + game.getId() + ")")));
				link.add(new AttributeModifier("id", true, Model.of("gameRowLink" + game.getId())));
				link.add(new Label("state", game.isMyTurn(opp) ? "Waiting... " : "My Turn!"));
				item.add(link);
				item.add(new Label("lastMove", formatDate(game.getLastModified())));
				item.add(new Label("noMoves", String.valueOf(game.getStates().size())));
			}
		};
		add(view);
	}
}
