package net.kalahau.data.manager;

import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;
import net.kalaha.data.manager.ManagerModule;

import org.testng.annotations.BeforeMethod;

import com.cubeia.firebase.api.service.ServiceRegistryAdapter;
import com.cubeia.firebase.guice.inject.FirebaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class JpaTestBase {

	protected Injector injector;
	protected UserManager userManager;
	protected GameManager gameManager;
	
	@BeforeMethod
	public void setUp() throws Exception {
		injector = Guice.createInjector(
							new FirebaseModule(new ServiceRegistryAdapter()),
							new TestJpaModule(),
							new ManagerModule());
	
		userManager = injector.getInstance(UserManager.class);
		gameManager = injector.getInstance(GameManager.class);
	}
}
