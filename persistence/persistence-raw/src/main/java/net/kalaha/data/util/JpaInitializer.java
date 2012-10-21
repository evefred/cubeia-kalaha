package net.kalaha.data.util;

import com.google.inject.Inject;
import com.google.inject.persist.PersistService;

public class JpaInitializer {

	@Inject
	public JpaInitializer(PersistService service) {
		service.start();
	}
}
