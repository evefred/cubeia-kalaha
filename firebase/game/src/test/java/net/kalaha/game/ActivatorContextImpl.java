package net.kalaha.game;

import java.util.Map;

import net.kalaha.table.api.GetTableResponse;
import net.kalaha.table.api.TableManager;
import net.kalaha.table.api.TableRequestAction;

import com.cubeia.firebase.api.action.service.ServiceAction;
import com.cubeia.firebase.api.common.AttributeValue;
import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.CreationParticipant;
import com.cubeia.firebase.api.game.activator.TableFactory;
import com.cubeia.firebase.api.game.lobby.LobbyTable;
import com.cubeia.firebase.api.game.lobby.LobbyTableFilter;
import com.cubeia.firebase.api.lobby.LobbyPath;
import com.cubeia.firebase.api.routing.ActivatorRouter;
import com.cubeia.firebase.api.service.Contract;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.api.service.ServiceRegistryAdapter;
import com.cubeia.firebase.api.service.ServiceRouter;
import com.cubeia.firebase.api.util.ConfigSource;
import com.cubeia.firebase.api.util.ConfigSourceListener;

public class ActivatorContextImpl implements ActivatorContext {

	int foundTableId;

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
				if(contract.equals(TableManager.class)) {
					return (T)new TableManager() {

						@Override
						public void setRouter(ServiceRouter arg0) { }
						
						@Override
						public void onAction(ServiceAction arg0) { }
						
						@Override
						public void sendToClient(TableRequestAction action) {
							foundTableId = ((GetTableResponse) action).getTableId();
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
		return new TableFactory() {
			
			@Override
			public LobbyTable[] listTables(LobbyPath arg0, LobbyTableFilter arg1) {
				return null;
			}
			
			@Override
			public LobbyTable[] listTables(LobbyPath arg0) {
				return null;
			}
			
			@Override
			public LobbyTable[] listTables(LobbyTableFilter arg0) {
				return null;
			}
			
			@Override
			public LobbyTable[] listTables() {
				return null;
			}
			
			@Override
			public boolean destroyTable(LobbyTable arg0, boolean arg1) {
				return false;
			}
			
			@Override
			public boolean destroyTable(int arg0, boolean arg1) {
				return false;
			}
			
			@Override
			public LobbyTable[] createTables(int arg0, int arg1, CreationParticipant arg2) {
				return null;
			}
			
			@Override
			public LobbyTable createTable(int arg0, CreationParticipant arg1) {
				return new LobbyTable() {

					private static final long serialVersionUID = -6918468350808393087L;

					@Override
					public int getObjectId() {
						return 666;
					}
					
					@Override
					public Map<String, AttributeValue> getAttributes() {
						return null;
					}
					
					@Override
					public int getTableId() {
						return 666;
					}
				};
			}
		};
	}

	@Override
	public void setConfigSourceListener(ConfigSourceListener arg0) { }
}
