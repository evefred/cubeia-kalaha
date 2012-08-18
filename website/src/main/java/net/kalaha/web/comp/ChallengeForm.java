package net.kalaha.web.comp;

import static net.kalaha.data.entities.GameType.KALAHA;
import static net.kalaha.game.logic.KalahaBoard.getInitState;
import static net.kalaha.web.Alert.Type.ERROR;
import static net.kalaha.web.Alert.Type.INFO;
import static net.kalaha.web.Alert.Type.SUCCESS;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.web.Alert;
import net.kalaha.web.Challenge;
import net.kalaha.web.Index;
import net.kalaha.web.KalahaSession;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class ChallengeForm extends StatelessForm<Void> {

	private static final long serialVersionUID = -5791192022212692228L;
	
	private TextField<String> username;
	
	private final ValueMap properties = new ValueMap();

	private final GameManager gameManager;

	private final UserManager userManager;

	private Class<? extends Page> challengeClass;
	private Class<? extends Page> indexClass;
	
	public ChallengeForm(String id, GameManager gameManager, UserManager userManager) {
		super(id);
		this.gameManager = gameManager;
		this.userManager = userManager;
		add(username = new TextField<String>("username", new PropertyModel<String>(properties, "username")));
		username.setType(String.class);
		this.challengeClass = Challenge.class;
		this.indexClass = Index.class;
		add(new BookmarkablePageLink<Void>("cancel-button", indexClass));
	}

	@Override
	public final void onSubmit() {
		KalahaSession ses = getKalahaSession();
		String name = username.getDefaultModelObjectAsString();
		User opponent = userManager.getUserByLocalName(name);
		if(opponent == null) {
			ses.setAlert(new Alert(ERROR, "User with name " + name + " not found."));
			setResponsePage(challengeClass);
		} else if(opponent.getId() == ses.getUser().getId()) {
			ses.setAlert(new Alert(INFO, "You can't play with yourself silly!"));
			setResponsePage(challengeClass);
		} else {
			ses.setAlert(new Alert(SUCCESS, "Game created against " + opponent.getLocalName()));
			gameManager.createGame(KALAHA, ses.getUser(), opponent, -1, getInitState(6));
			setResponsePage(indexClass);
		}
	}

	public String getUsername() {
		return username.getDefaultModelObjectAsString();
	}
	
	public KalahaSession getKalahaSession() {
		return (KalahaSession) getSession();
	}
}
