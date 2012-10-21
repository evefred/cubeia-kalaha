package net.kalahau.data.manager;

import net.kalaha.common.util.SystemTime;

import com.google.inject.AbstractModule;

public class TestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SystemTime.class).to(SystemTestTime.class);
	}

}
