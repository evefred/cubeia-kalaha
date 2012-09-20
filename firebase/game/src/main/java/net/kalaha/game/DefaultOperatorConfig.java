package net.kalaha.game;

import static net.kalaha.common.Constants.BOT_OPERATOR_ID;

import com.google.inject.Singleton;

@Singleton
public class DefaultOperatorConfig implements OperatorConfig {

	@Override
	public long getDefaultMoveTimeout(int operatorId) {
		if(operatorId == BOT_OPERATOR_ID) {
			return 60000;
		} else {
			return -1; // no timeout
		}
	}
}
