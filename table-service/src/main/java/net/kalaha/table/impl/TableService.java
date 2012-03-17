package net.kalaha.table.impl;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

import net.kalaha.table.api.TableManager;
import net.kalaha.table.api.TableRequestAction;

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
	private Transformer transformer = new Transformer();
	
	private final Logger log = Logger.getLogger(getClass());
	
	/*@Override
	public void tableLocated(GetTableRequest query, int tableId) {
		JSONObject o = new JSONObject();
		o.put("gameId", query.gameId);
		o.put("tableId", tableId);
		byte[] data = toUTF8Data(o.toString());
		ServiceAction a = new ClientServiceAction(query.userId, query.correlationId, data);
		router.dispatchToPlayer(query.userId, a);
	}*/
	
	@Override
	public void sendToClient(TableRequestAction action) {
		byte[] data = transformer.toUTF8Data(action);
		ServiceAction a = new ClientServiceAction(action.getUserId(), action.getCorrelationId(), data);
		router.dispatchToPlayer(action.getUserId(), a);
	}

	@Override
	public void onAction(ServiceAction action) {
		/*int pid = action.getPlayerId();
		String json = fromUTF8Data(action.getData());
		JSONObject o = JSONObject.fromObject(json);
		int gameId = o.getInt("gameId");
		GetTableRequest q = new GetTableRequest(pid, action.getSeq(), gameId);
		ActivatorAction<GetTableRequest> request = new ActivatorAction<GetTableRequest>(q);
		router.dispatchToGameActivator(GAME_ID, request);*/
		
		String json = null;
		try {
			json = new String(action.getData(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Incoming JSON: " + json);
		TableRequestAction act = (TableRequestAction) transformer.fromString(json);
		ActivatorAction<TableRequestAction> request = new ActivatorAction<TableRequestAction>(act);
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
	
}
