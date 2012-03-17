package net.kalaha.game.action;

import net.kalaha.json.JsonTransformer;

public class Transformer extends JsonTransformer {

	@Override
	protected String getActionPackage() {
		return Sow.class.getPackage().getName();
	}
}
