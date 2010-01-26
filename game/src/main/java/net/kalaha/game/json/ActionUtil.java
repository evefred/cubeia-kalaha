package net.kalaha.game.json;

import java.nio.ByteBuffer;

import com.cubeia.firebase.api.action.GameDataAction;

public class ActionUtil {

	public static GameDataAction toDataAction(int playerId, int tableId, Object action) {
		GameDataAction gda = new GameDataAction(playerId, tableId);
		gda.setData(ByteBuffer.wrap(ActionTransformer.toUTF8Data(action)));
		return gda;
	}
}
