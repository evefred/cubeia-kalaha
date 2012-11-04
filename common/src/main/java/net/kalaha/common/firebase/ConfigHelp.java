package net.kalaha.common.firebase;

import java.util.Properties;

import com.cubeia.firebase.api.server.conf.ConfigProperty;
import com.cubeia.firebase.api.server.conf.PropertyKey;
import com.cubeia.firebase.api.service.ServiceRegistry;
import com.cubeia.firebase.api.service.config.ClusterConfigProviderContract;

public class ConfigHelp {

	private ConfigHelp() { }
	
	public static Properties getClusterProperties(ServiceRegistry reg) {
		ClusterConfigProviderContract serv = reg.getServiceInstance(ClusterConfigProviderContract.class);
		Properties p = new Properties();
		for (ConfigProperty prop : serv.getAllProperties()) {
			PropertyKey key = prop.getKey();
			String name = key.getNamespace() + "." + key.getProperty();
			if(name.startsWith(".")) {
				name = key.getProperty();
			}
			p.put(name, prop.getValue());
		}
		return p;
	}
}
