package net.kalahau.user.impl;

import net.kalaha.user.api.UserManager;
import net.kalaha.user.impl.UserModule;

import org.testng.annotations.BeforeMethod;

import com.cubeia.firebase.api.service.ServiceRegistryAdapter;
import com.cubeia.firebase.guice.inject.FirebaseModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class JpaTestBase {

	protected Injector injector;
	protected UserManager manager;
	
	@BeforeMethod
	public void setUp() throws Exception {
		injector = Guice.createInjector(
							new FirebaseModule(new ServiceRegistryAdapter()),
							new TestJpaModule(),
							new UserModule());
	
		manager = injector.getInstance(UserManager.class);
	}
}
