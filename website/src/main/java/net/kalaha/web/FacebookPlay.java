package net.kalaha.web;

import static net.kalaha.web.Alert.Type.ERROR;
import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.Session;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

@AuthorizeInstantiation("USER")
public class FacebookPlay extends FacebookBasePage {

	private int gameId;

	public FacebookPlay(PageParameters p) {
		super(p);
		setup();
	}
	
	@Override
	public void renderHead(HtmlHeaderContainer container) {
		super.renderHead(container);
		// container.getHeaderResponse().renderCSSReference(
		// renderApiKey(container);
		renderJsConfig(container);
	}

	
	// --- PRIVATE METHODS --- //
	
	private void setup() {
		KalahaSession ses = getKalahaSession();
		gameId = getPageParameters().getAsInteger("gameId", new Integer(-1));
		Game game = gameManager.getGame(gameId);
		if(game == null) {
			ses.setAlert(new Alert(ERROR, "Game not found: " + gameId));
			setResponsePage(FacebookIndex.class);
		} else {
			add(new BookmarkablePageLink<Void>("go-back", FacebookIndex.class));
		}
	}
	
	private void renderJsConfig(HtmlHeaderContainer container) {
		Session dbSession = getKalahaSession().getSession();
		String js = "__session=\"" + dbSession.getId() + "\";" +
				"__operatorId=" + operatorId + ";" +
				"__firebaseHost=\"" + firebaseHost + "\";" +
				"__firebasePort=\"" + firebasePort + "\";" +
				"__gameId=\"" + gameId + "\";";
		container.getHeaderResponse().renderJavascript(js, "jsConf");
	}
}
