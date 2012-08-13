package net.kalaha.table.api;

import com.cubeia.firebase.api.service.Contract;
import com.cubeia.firebase.api.service.RoutableService;

public interface TableManager extends Contract, RoutableService {

	public void sendToClient(TableRequestAction action);
	
}
