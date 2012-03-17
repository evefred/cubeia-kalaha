package net.kalaha.game.action.util;

import java.nio.ByteBuffer;

import net.kalaha.json.ActionTransformer;


import com.cubeia.firebase.api.action.GameDataAction;
import com.google.inject.Inject;

public class ActionUtil {

	@Inject
	private ActionTransformer trans;
	
	public GameDataAction toDataAction(int playerId, int tableId, Object action) {
		GameDataAction gda = new GameDataAction(playerId, tableId);
		gda.setData(ByteBuffer.wrap(trans.toUTF8Data(action)));
		return gda;
	}
}
