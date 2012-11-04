package net.kalaha.notice.impl;

import net.kalaha.common.json.JsonTransformer;
import net.kalaha.notice.api.NextMoveNotice;

public class Transformer extends JsonTransformer {

	@Override
	protected String getActionPackage() {
		return NextMoveNotice.class.getPackage().getName();
	}
}
