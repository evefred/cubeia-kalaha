package net.kalaha.web.comp;

import static net.kalaha.web.Alert.Type.ERROR;
import static net.kalaha.web.Alert.Type.SUCCESS;
import net.kalaha.web.Alert;
import net.kalaha.web.Index;
import net.kalaha.web.KalahaSession;
import net.kalaha.web.Login;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.value.ValueMap;

public class LoginForm extends StatelessForm<Void> {

	private PasswordTextField password;
	private TextField<String> username;
	
	private final ValueMap properties = new ValueMap();

	private static final long serialVersionUID = -5791192022212692228L;
	
	public LoginForm(String id) {
		super(id);
		add(username = new TextField<String>("username", new PropertyModel<String>(properties, "username")));
		add(password = new PasswordTextField("password", new PropertyModel<String>(properties, "password")));
		username.setType(String.class);
		password.setType(String.class);
	}
	
	@Override
	public final void onSubmit() {
		if (signIn(getUsername(), getPassword())) {
			onSignInSucceeded();
		} else {
			onSignInFailed();
		}
	}

	public String getPassword() {
		return password.getInput();
	}

	public String getUsername() {
		return username.getDefaultModelObjectAsString();
	}
	
	public KalahaSession getKalahaSession() {
		return (KalahaSession) getSession();
	}
	
	
	// --- PROTECTED METHODS --- //
	
	protected boolean signIn(String username, String password) {
		return AuthenticatedWebSession.get().signIn(username, password);
	}

	protected void onSignInFailed() { 
		KalahaSession ses = getKalahaSession();
		ses.setAlert(new Alert(ERROR, "No such user."));
		setResponsePage(Login.class);
	}

	protected void onSignInSucceeded() {
		KalahaSession ses = getKalahaSession();
		ses.setAlert(new Alert(SUCCESS, "Welcome " + ses.getDisplayName() + "!"));
		if (!continueToOriginalDestination()) {
			throw new RestartResponseException(Index.class);
		}
	}
}
