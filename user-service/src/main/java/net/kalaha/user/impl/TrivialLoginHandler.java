package net.kalaha.user.impl;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;
import com.cubeia.firebase.api.login.LoginHandler;

public class TrivialLoginHandler implements LoginHandler {

	@Override
	public LoginResponseAction handle(LoginRequestAction req) {
		return new LoginResponseAction(true, Integer.parseInt(req.getPassword()));
	}
}
