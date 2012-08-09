package net.kalaha.game.action.util;

import java.nio.ByteBuffer;

import net.kalaha.common.json.AbstractAction;
import net.kalaha.common.json.ActionTransformer;


import com.cubeia.firebase.api.action.GameDataAction;
import com.google.inject.Inject;

public class ActionUtil {

	@Inject
	private ActionTransformer trans;
	
	public <T extends AbstractAction> GameDataAction toDataAction(int playerId, int tableId, T action) {
		GameDataAction gda = new GameDataAction(playerId, tableId);
		gda.setData(ByteBuffer.wrap(trans.toUTF8Data(action)));
		return gda;
	}
}
