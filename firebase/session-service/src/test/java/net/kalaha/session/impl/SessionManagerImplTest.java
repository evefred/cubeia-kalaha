package net.kalaha.session.impl;

import static net.kalaha.common.util.Reflection.getPrivateDeclaredField;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import net.kalaha.common.guice.PropertiesModule;
import net.kalaha.data.manager.SessionManager;

import org.testng.annotations.Test;

import com.cubeia.firebase.api.server.conf.ConfigProperty;
import com.cubeia.firebase.api.server.conf.Configurable;
import com.cubeia.firebase.api.server.conf.ConfigurationException;
import com.cubeia.firebase.api.server.conf.Namespace;
import com.cubeia.firebase.api.server.conf.PropertyKey;
import com.cubeia.firebase.api.service.ServiceContextAdapter;
import com.cubeia.firebase.api.service.ServiceRegistryAdapter;
import com.cubeia.firebase.api.service.config.ClusterConfigProviderContract;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Provider;

public class SessionManagerImplTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testInit() throws Exception {
		SessionManagerImpl impl = new SessionManagerImpl() {
			
			@Override
			protected void createInjector() {
				ClusterConfigProviderContract serv = con.getParentRegistry().getServiceInstance(ClusterConfigProviderContract.class);
				injector = Guice.createInjector(new TestModule(), new PropertiesModule(getConfigProperties(serv)));
				injector.injectMembers(this);
			}
		};
		impl.init(new Context());
		SessionReaper reaper = (SessionReaper) getPrivateDeclaredField(impl, "reaper");
		assertEquals(getPrivateDeclaredField(reaper, "interval"), 10L);
		Provider<ReaperTask> prov = (Provider<ReaperTask>) getPrivateDeclaredField(reaper, "tasks");
		assertEquals(getPrivateDeclaredField(prov.get(), "maxAge"), 10L);
	}
	
	
	// --- PRIVATE CLASSES --- //
	
	private class TestModule extends AbstractModule {
		
		@Override
		protected void configure() {
			bind(SessionManager.class).toInstance(mock(SessionManager.class));
		}
	}
	
	private class Context extends ServiceContextAdapter {
		
		private Context() {
			reg = new Registry();
		}
	}
	
	private class Registry extends ServiceRegistryAdapter {
		
		private Registry() {
			super.addImplementation(ClusterConfigProviderContract.class, new ClusterConfigProviderContract() {
				
				@Override
				public <T extends Configurable> T getConfiguration(Class<T> cl, Namespace ns) throws ConfigurationException {
					throw new UnsupportedOperationException();
				}
				
				@Override
				public ConfigProperty[] getAllProperties() {
					return new ConfigProperty[] {
						new ConfigProperty(new PropertyKey(new Namespace("service.sessions"), "max-age"), "10"),
						new ConfigProperty(new PropertyKey(new Namespace("service.sessions"), "reaper-interval"), "10")
					};
				}
			});
		}
	}
}
