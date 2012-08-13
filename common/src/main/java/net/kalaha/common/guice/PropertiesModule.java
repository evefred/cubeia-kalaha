package net.kalaha.common.guice;

import java.util.Properties;

public class PropertiesModule extends PropertiesBinderModule {

	private final Properties props;

	public PropertiesModule(Properties props) {
		this.props = props;
	}
	
	@Override
	protected Properties loadProperties() {
		return props;
	}
}
