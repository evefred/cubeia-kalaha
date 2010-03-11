package net.kalaha.data.manager;

import java.util.Collection; 

import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;

public interface GameManager {
	
	public Game getGame(int gameId);

	public Collection<Game> getMyGames(User user, GameStatus status, GameForm form);
	
	public Game createGame(GameType type, GameForm form, User owner, User opponent, long moveTimeout);
	
	public Game finishGame(int gameId, User winner);
	
}
