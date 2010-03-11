package net.kalaha.game;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.TableFactory;
import com.cubeia.firebase.api.routing.ActivatorRouter;
import com.cubeia.firebase.api.service.Contract;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.api.service.ServiceRegistryAdapter;
import com.cubeia.firebase.api.service.persistence.PublicPersistenceService;
import com.cubeia.firebase.api.util.ConfigSource;
import com.cubeia.firebase.api.util.ConfigSourceListener;

public class ActivatorContextImpl implements ActivatorContext {

	@Override
	public ActivatorRouter getActivatorRouter() {
		return null;
	}

	@Override
	public ConfigSource getConfigSource() {
		return null;
	}

	@Override
	public int getGameId() {
		return 0;
	}

	@Override
	public ServiceRegistry getServices() {
		return new ServiceRegistryAdapter() {
			
			@Override
			@SuppressWarnings("unchecked")
			public <T extends Contract> T getServiceInstance(Class<T> contract) {
				if(contract.equals(PublicPersistenceService.class)) {
					return (T)new PublicPersistenceService() {
						
						EntityManagerFactory f = Persistence.createEntityManagerFactory("kalaha");
						
						@Override
						public boolean isReady(String arg0) {
							return true;
						}
						
						@Override
						public EntityManager getEntityManager(String arg0, boolean arg1) {
							return null;
						}
						
						@Override
						public EntityManager getEntityManager(String arg0) {
							return f.createEntityManager();
						}
					};
				} else {
					return super.getServiceInstance(contract);
				}
			}
		};
	}

	@Override
	public TableFactory getTableFactory() {
		return null;
	}

	@Override
	public void setConfigSourceListener(ConfigSourceListener arg0) { }
}
