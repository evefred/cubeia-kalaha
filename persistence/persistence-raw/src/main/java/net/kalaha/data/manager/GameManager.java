package net.kalaha.data.manager;

import java.util.List;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameResult;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.GameType;
import net.kalaha.data.entities.Request;
import net.kalaha.data.entities.User;

public interface GameManager {
	
	public Game getGame(long gameId);
	
	public List<Game> getMyGames(User user, GameStatus status);
	
	public Game createGame(GameType type, User owner, User opponent, long moveTimeout, int[] initState);
	
	public Game createGame(GameType type, Request request);
	
	public Game finishGame(long gameId, long winnerId, GameResult result);
	
	public List<Game> reapGames(int maxAgeDays);
	
	public Game updateGame(Request request, boolean accepted);

	public void updateGame(long gameId, int[] state, boolean ownersMove);

	public Game getGame(Request request);
	
}
