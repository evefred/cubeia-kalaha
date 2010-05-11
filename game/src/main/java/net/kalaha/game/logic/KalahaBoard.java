package net.kalaha.game.logic;

import java.io.Serializable;

import net.kalaha.entities.Game;
import net.kalaha.entities.GameStatus;
import net.kalaha.game.action.State;

import org.apache.log4j.Logger;

public class KalahaBoard implements Serializable {

	private static final Logger log = Logger.getLogger(KalahaBoard.class);

	private static final long serialVersionUID = -3348427879794771338L;

	private final static int NUMBER_OF_PITS = 6;
	
	public final static int SOUTH_KALAHA = 6;
	public final static int NORTH_KALAHA = 13;

	private final State state;
	
	private int southPlayerId;
	private int northPlayerId;
	private int gameId;

	private boolean gameEnded;
	private Player playerToAct;

	private SpecialRules rules;
	
	public KalahaBoard(int stones) {		
		this(stones, Player.SOUTH);
	}
	
	public KalahaBoard(Game game) {
		this.gameId = game.getId();
		this.southPlayerId = game.getOwner().getId();
		if (game.getOpponent() != null) {
			this.northPlayerId = game.getOpponent().getId();
		}
		this.gameEnded = (game.getStatus() == GameStatus.ACTIVE ? false : true);
		this.playerToAct = (game.isOwnersMove() ? Player.SOUTH : Player.NORTH);
		this.state = new State(game.getCurrentGameState().getRealState(), southPlayerId, northPlayerId);
		this.rules = new DefaultRules();
	}
	
	public KalahaBoard(int stones, Player startingPlayer) {
		this(stones, startingPlayer, new DefaultRules());
	}
	
	public KalahaBoard(int stones, Player startingPlayer, SpecialRules rules) {
		this.state = new State();
		initState(stones);
		playerToAct = startingPlayer;
		this.rules = rules;		
	}

	public static int[] getInitState(int stones) {
		return new KalahaBoard(stones).getState().getPits();
	}	

	private void initState(int stones) {
		for (int i = 0; i < state.getPits().length; i++) {
			state.getPits()[i] = stones;
		}
		setStonesInKalaha(0, Player.SOUTH);
		setStonesInKalaha(0, Player.NORTH);
	}
	
	public void updateGame(Game game) {
		game.setLastModified(System.currentTimeMillis());
		game.updateGameState(state.getPits());
		game.setOwnersMove(playerToAct == Player.SOUTH);
		if(gameEnded) {
			game.setStatus(GameStatus.FINISHED);
		}
	}
	
	public int getGameId() {
		return gameId;
	}
	
	public State getState() {
		return state;
	}
	
	public int getNorthPlayerId() {
		return northPlayerId;
	}
	
	public int getSouthPlayerId() {
		return southPlayerId;
	}
	
	public void setNorthPlayerId(int northPlayerId) {
		this.northPlayerId = northPlayerId;
	}
	
	public void setSouthPlayerId(int southPlayerId) {
		this.southPlayerId = southPlayerId;
	}
	
	public Player getPlayerForId(int playerId) {
		if(playerId == southPlayerId) {
			return Player.SOUTH;
		} else if(playerId == northPlayerId) {
			return Player.NORTH;
		} else {
			throw new IllegalStateException("No such player: " + playerId);
		}
	}

	public int getStonesInKalaha(Player player) {
		return state.getPits()[player.kalaha()];
	}
	
	public void setStonesInKalaha(int stones, Player player) {
		state.getPits()[player.kalaha()] = stones;
	}

	int getStonesInPit(int pit, Player player) {
		if (player == Player.SOUTH) {
			return state.getPits()[pit];
		} else {
			return state.getPits()[SOUTH_KALAHA + 1 + pit];
		}
	}
	
	public void moveStones(int pit, Player player) {
		if (!playerToAct(player)) {
			log.debug("Skipping player " + player + " silently, it's not his turn...");
			return;
		}
		
		int stonesToMove = getStonesInPit(pit, player);
		if (stonesToMove == 0) {
			log.debug("Ignoring move from " + player + " silently, tried to move zero stones...");
			return;			
		}
		
		boolean endedInKalaha = false;
		setStonesInPit(0, pit, player);
		
		int skippedKalahas = 0;
		int offset = (player == Player.NORTH) ? NUMBER_OF_PITS + 1 : 0;
		for (int i = pit + 1; i < pit + stonesToMove + 1; i++) {
			int currentPit = (i + skippedKalahas + offset) % state.getPits().length;
			
			// Skip opponent's kalaha
			if (isOpponentKalaha(player, currentPit)) {
				skippedKalahas++;
				currentPit = (i + skippedKalahas + offset) % state.getPits().length;
			}
			
			// Steal opponent's stones if last stone lands in one of my empty pits.			
			boolean lastStone = i == (pit + stonesToMove);
			boolean myEmptyPit = player.isMyPit(currentPit) && state.getPits()[currentPit] == 0;
			boolean myKalaha = player.isMyKalaha(currentPit);
			boolean stole = false;
			
			if (lastStone && myEmptyPit) {				
				stole = stealOpponentsStones(player, currentPit);
				endedInKalaha = stole;
			} else if (lastStone && myKalaha) {
				endedInKalaha = true;
			}
			
			if (!stole) {
				state.getPits()[currentPit]++;
			}
		}
		
		updatePlayerToAct(player, endedInKalaha);
		checkGameEnd(player);
	}

	private void updatePlayerToAct(Player playerWhoActed, boolean endedInKalaha) {
		if (!endedInKalaha) {
			playerToAct = getOpponent(playerWhoActed);
		}
	}

	private boolean playerToAct(Player player) {
		return playerToAct == player;
	}

	private boolean stealOpponentsStones(Player player, int currentPit) {
		boolean stole = false;
		int currentStonesInKalaha = getStonesInKalaha(player);		
		int stonesInOpponentPit = getStonesInOpponentPit(player, currentPit);
		if (stonesInOpponentPit > 0 || rules.allowStealingFromEmptyPit()) {
			setStonesInKalaha(currentStonesInKalaha + 1 + stonesInOpponentPit, player);
			setStonesInPit(0, getOpponentPit(player, currentPit), getOpponent(player));
			stole = true;
		}
		return stole;
	}

	private int getStonesInOpponentPit(Player player, int currentPit) {
		int opponentPit = getOpponentPit(player, currentPit);
		return getStonesInPit(opponentPit, getOpponent(player));
	}

	private int getOpponentPit(Player player, int currentPit) {
		int opponentPit = NUMBER_OF_PITS - player.toLocalPit(currentPit) - 1;
		return opponentPit;
	}
	
	private boolean isOpponentKalaha(Player player, int currentPit) {
		return getOpponent(player).isMyKalaha(currentPit);
	}

	
	private void checkGameEnd(Player player) {
		if (!canPlayerMove(player)) {
			if (playerToAct == player || rules.endGameWhenEitherPlayerRunsOutOfStones()) {
				gameEnded = true;
			}
		} else if (!canPlayerMove(getOpponent(player))) {
			endTransfer(player);
			gameEnded = true;
		}
	}
	
	public void endTransfer(Player player) {
		// Move remaining stones to kalaha
		for (int i = 0; i < 6; i++) {
			int remaining = getStonesInPit(i, player);
			if(remaining > 0) {
				int tot = getStonesInKalaha(player) + remaining;
				setStonesInKalaha(tot, player);
				setStonesInPit(0, i, player);
			}
		}
	}

	public boolean canPlayerMove(Player player) {
		for (int i = 0; i < 6; i++) {
			if(getStonesInPit(i, player) != 0) {
				return true;
			}
		}
		return false;
	}

	private Player getOpponent(Player player) {
		return (player == Player.SOUTH) ? Player.NORTH : Player.SOUTH;
	}

	public void setStonesInPit(int stones, int pit, Player player) {
		if (player == Player.SOUTH) {
			state.getPits()[pit] = stones;
		} else {
			state.getPits()[NUMBER_OF_PITS + pit + 1] = stones;
		}		
	}

	public boolean isGameEnded() {
		return gameEnded;
	}
	
	public boolean isDraw() {
		return getStonesInKalaha(Player.SOUTH) == getStonesInKalaha(Player.NORTH);
	}

	public int getWinningPlayerId() {
		if(getStonesInKalaha(Player.SOUTH) > getStonesInKalaha(Player.NORTH)) {
			return southPlayerId;
		} else if(getStonesInKalaha(Player.SOUTH) < getStonesInKalaha(Player.NORTH)) {
			return northPlayerId;
		} else {
			return -1;
		}
	}

	public Player getPlayerToAct() {
		return playerToAct;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int pit : state.getPits()) {
			sb.append(pit + ",");
		}
		return sb.toString();
	}

	public void setPlayerToAct(Player player) {
		playerToAct = player;
	}
}
