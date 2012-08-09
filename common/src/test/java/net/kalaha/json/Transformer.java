package net.kalaha.json;

import net.kalaha.common.json.JsonTransformer;

public class Transformer extends JsonTransformer {

	@Override
	protected String getActionPackage() {
		return TestAction.class.getPackage().getName();
	}
}
