package net.kalahau.user.impl;

import net.kalaha.entities.Session;
import net.kalaha.user.impl.KalahaLoginHandler;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cubeia.firebase.api.action.local.LoginRequestAction;
import com.cubeia.firebase.api.action.local.LoginResponseAction;

public class KalahaLoginHandlerTest extends JpaTestBase {

	@Test
	public void testLogin() throws Exception {
		KalahaLoginHandler lh = injector.getInstance(KalahaLoginHandler.class);
		Session ses = manager.getSessionByExternalId("kalle", 0);
		LoginRequestAction req = new LoginRequestAction("user", ses.getId(), 0);
		LoginResponseAction resp = lh.handle(req);
		Assert.assertTrue(resp.isAccepted());
	}
}
