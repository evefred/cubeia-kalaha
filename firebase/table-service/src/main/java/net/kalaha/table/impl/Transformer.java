package net.kalaha.table.impl;

import net.kalaha.common.guice.ObjectMapperProvider;
import net.kalaha.common.json.JsonTransformer;
import net.kalaha.table.api.TableRequestAction;

public class Transformer extends JsonTransformer {

	public Transformer() {
		super.map = new ObjectMapperProvider().get();
	}
	
	@Override
	protected String getActionPackage() {
		return TableRequestAction.class.getPackage().getName();
	}
}
