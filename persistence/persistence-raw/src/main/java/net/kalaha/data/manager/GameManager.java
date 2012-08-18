package net.kalaha.data.manager;

import java.util.List;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.GameType;
import net.kalaha.data.entities.User;

public interface GameManager {
	
	public Game getGame(long gameId);
	
	public List<Game> getMyGames(User user, GameStatus status);
	
	public Game createGame(GameType type, User owner, User opponent, long moveTimeout, int[] initState);
	
	public Game finishGame(long gameId, User winner);
	
}
