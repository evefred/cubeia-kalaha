package net.kalaha.game.impl;

import com.cubeia.firebase.api.server.SystemException;
import com.cubeia.firebase.api.service.Service;
import com.cubeia.firebase.api.service.ServiceContext;

import net.kalaha.game.api.GameService;

public class GameServiceImpl implements Service, GameService {
	
	public void init(ServiceContext con) throws SystemException { }

	public void start() { }

	public void stop() {}
	
	public void destroy() {}

}