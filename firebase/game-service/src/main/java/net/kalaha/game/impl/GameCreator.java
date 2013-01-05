package net.kalaha.game.impl;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameType;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.GameManager;
import net.kalaha.data.manager.UserManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class GameCreator implements GameCreatorMBean {

	@Inject
	private GameManager games;
	
	@Inject
	private UserManager users;
	
	@Override
	public long createGame(long userOne, long userTwo) {
		User one = users.getUser(userOne);
		User two = users.getUser(userTwo);
		Game game = games.createGame(GameType.KALAHA, one, two, -1, null);
		return game.getId();
	}

}
