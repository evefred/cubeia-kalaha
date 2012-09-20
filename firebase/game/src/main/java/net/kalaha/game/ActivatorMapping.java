package net.kalaha.game;

import java.util.HashMap;
import java.util.Map;

public class ActivatorMapping {
	
	private Map<Long, Integer> gameToTable = new HashMap<Long, Integer>();
	private Map<Integer, Long> tableToGame = new HashMap<Integer, Long>();

	public void put(long gameId, int tableId) {
		gameToTable.put(gameId, tableId);
		tableToGame.put(tableId, gameId);
	}
	
	public int getTableForGame(long gameId) {
		Integer i = gameToTable.get(gameId);
		return (i == null ? -1 : i.intValue());
	}
	
	public void removeForTable(int tableId) {
		Long i = tableToGame.remove(tableId);
		if(i != null) {
			gameToTable.remove(i);
		}
	}
}