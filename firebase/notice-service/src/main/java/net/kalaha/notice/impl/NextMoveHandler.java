package net.kalaha.notice.impl;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.GameManager;
import net.kalaha.notice.api.NextMoveNotice;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class NextMoveHandler {

	@Inject
	private GameManager gameManager;
	
	@Inject
	private Provider<FacebookRequestSender> senders;
	
	private final Logger log = Logger.getLogger(getClass());
	
	public void handle(NextMoveNotice notice) {
		Game game = gameManager.getGame(notice.getGameId());
		boolean isOwners = game.isOwnersMove();
		User moving = (game.getOwner().getId() == notice.getUserId() ? game.getOwner() : game.getOpponent());
		User waiting = (isOwners ? game.getOpponent() : game.getOwner());
		log.debug("Moving user " + moving.getId() + " in game " + game.getId());
		senders.get().sendRequest(moving.getExternalId(), waiting.getUserDetails().getDisplayName() + " has made a move, it's your turn!");
	}
}
