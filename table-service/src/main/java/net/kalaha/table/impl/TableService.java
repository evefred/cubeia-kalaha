package net.kalaha.table.impl;

import java.io.UnsupportedEncodingException;

import net.kalaha.table.api.TableManager;
import net.kalaha.table.api.TableQuery;
import net.sf.json.JSONObject;

import com.cubeia.firebase.api.action.service.ClientServiceAction;
import com.cubeia.firebase.api.action.service.ServiceAction;
import com.cubeia.firebase.api.routing.ActivatorAction;
import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;
import com.cubeia.firebase.api.service.ServiceRouter;

public class TableService implements TableManager, Service {
	
	private static final int GAME_ID = 236;

	private ServiceRouter router;

	@Override
	public void tableLocated(TableQuery query, int tableId) {
		JSONObject o = new JSONObject();
		o.put("gameId", query.gameId);
		o.put("tableId", tableId);
		byte[] data = toUTF8Data(o.toString());
		ServiceAction a = new ClientServiceAction(query.userId, query.correlationId, data);
		router.dispatchToPlayer(query.userId, a);
	}

	@Override
	public void onAction(ServiceAction action) {
		int pid = action.getPlayerId();
		String json = fromUTF8Data(action.getData());
		JSONObject o = JSONObject.fromObject(json);
		int gameId = o.getInt("gameId");
		TableQuery q = new TableQuery(pid, action.getSeq(), gameId);
		ActivatorAction<TableQuery> request = new ActivatorAction<TableQuery>(q);
		router.dispatchToGameActivator(GAME_ID, request);
	}

	@Override
	public void setRouter(ServiceRouter router) {
		this.router = router; 
	}

	@Override
	public void destroy() { }

	@Override
	public void init(ServiceContext context) throws SystemException { }

	@Override
	public void start() { }

	@Override
	public void stop() { }
	
	
	// --- PRIVATE METHODS --- //
	
	private byte[] toUTF8Data(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
	
	private String fromUTF8Data(byte[] arr) {
		try {
			return new String(arr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
}
