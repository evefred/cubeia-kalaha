package net.kalaha.game;

import net.kalaha.common.json.JsonTransformer;
import net.kalaha.notice.api.NextMoveNotice;

public class NoticeTransformer extends JsonTransformer {

	@Override
	protected String getActionPackage() {
		return NextMoveNotice.class.getPackage().getName();
	}
}