package net.kalaha.json;

import net.kalaha.common.guice.ObjectMapperProvider;
import net.kalaha.common.json.JsonTransformer;

public class Transformer extends JsonTransformer {

	public Transformer() {
		super.map = new ObjectMapperProvider().get();
	}
	
	@Override
	protected String getActionPackage() {
		return TestAction.class.getPackage().getName();
	}
}
