package net.kalaha.game.logic;

import static net.kalaha.data.entities.GameResult.DRAW;
import static net.kalaha.data.entities.GameResult.WIN;
import static net.kalaha.data.entities.GameStatus.FINISHED;
import static net.kalaha.game.logic.KalahaPlayer.NORTH;
import static net.kalaha.game.logic.KalahaPlayer.SOUTH;

import java.io.Serializable;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.game.IllegalMoveException;
import net.kalaha.game.action.State;

import org.apache.log4j.Logger;

public class KalahaBoard implements Serializable {

	private static final Logger log = Logger.getLogger(KalahaBoard.class);

	private static final long serialVersionUID = -3348427879794771338L;

	private final static int NUMBER_OF_PITS = 6;
	
	public final static int SOUTH_KALAHA = 6;
	public final static int NORTH_KALAHA = 13;

	private final State state;
	
	private long southPlayerId;
	private long northPlayerId;
	private long gameId;

	private boolean gameEnded;
	private KalahaPlayer playerToAct;

	private SpecialRules rules;
	
	public KalahaBoard(int stones) {		
		this(stones, KalahaPlayer.SOUTH);
	}
	
	public KalahaBoard(Game game) {
		this.gameId = game.getId();
		this.southPlayerId = game.getOwner().getId();
		if (game.getOpponent() != null) {
			this.northPlayerId = game.getOpponent().getId();
		}
		this.gameEnded = (game.getStatus() == GameStatus.ACTIVE ? false : true);
		this.playerToAct = (game.isOwnersMove() ? KalahaPlayer.SOUTH : KalahaPlayer.NORTH);
		this.state = new State(game.getCurrentGameState().getRealState(), southPlayerId, northPlayerId, getPlayerToActId());
		this.rules = new DefaultRules();
	}
	
	public KalahaBoard(int stones, KalahaPlayer startingPlayer) {
		this(stones, startingPlayer, new DefaultRules());
	}
	
	public KalahaBoard(int stones, KalahaPlayer startingPlayer, SpecialRules rules) {
		this.state = new State();
		initState(stones);
		playerToAct = startingPlayer;
		state.setPlayerToAct(getPlayerToActId());
		this.rules = rules;		
	}

	public static int[] getInitState(int stones) {
		return new KalahaBoard(stones).getState().getHouses();
	}	
	
	private long getPlayerToActId() {
		if(playerToAct == KalahaPlayer.SOUTH) {
			return southPlayerId;
		} else {
			return northPlayerId;
		}
	}

	private void initState(int stones) {
		for (int i = 0; i < state.getHouses().length; i++) {
			state.getHouses()[i] = stones;
		}
		setStonesInKalaha(0, SOUTH);
		setStonesInKalaha(0, NORTH);
	}
	
	public void updateGame(Game game) {
		game.setLastModified(System.currentTimeMillis());
		game.updateGameState(state.getHouses());
		game.setOwnersMove(playerToAct == SOUTH);
		if(gameEnded) {
			game.setStatus(FINISHED);
			if(isDraw()) {
				game.setResult(DRAW);
			} else {
				game.setResult(WIN);
			}
		}
	}
	
	public long getGameId() {
		return gameId;
	}
	
	public State getState() {
		return state;
	}
	
	public long getNorthPlayerId() {
		return northPlayerId;
	}
	
	public long getSouthPlayerId() {
		return southPlayerId;
	}
	
	public void setNorthPlayerId(int northPlayerId) {
		this.northPlayerId = northPlayerId;
	}
	
	public void setSouthPlayerId(int southPlayerId) {
		this.southPlayerId = southPlayerId;
	}
	
	public KalahaPlayer getPlayerForId(int playerId) {
		if(playerId == southPlayerId) {
			return KalahaPlayer.SOUTH;
		} else if(playerId == northPlayerId) {
			return KalahaPlayer.NORTH;
		} else {
			throw new IllegalStateException("No such player: " + playerId);
		}
	}

	public int getStonesInKalaha(KalahaPlayer player) {
		return state.getHouses()[player.kalaha()];
	}
	
	public void setStonesInKalaha(int stones, KalahaPlayer player) {
		state.getHouses()[player.kalaha()] = stones;
	}

	int getStonesInPit(int pit, KalahaPlayer player) {
		if (player == KalahaPlayer.SOUTH) {
			return state.getHouses()[pit];
		} else {
			return state.getHouses()[SOUTH_KALAHA + 1 + pit];
		}
	}
	
	public void moveStones(int pit, KalahaPlayer player) {
		if(isGameEnded()) {
			log.debug("Skipping player " + player + " silently, the game is ended...");
			throw new IllegalMoveException();
		}
		
		if (!playerToAct(player)) {
			log.debug("Skipping player " + player + " silently, it's not his turn...");
			throw new IllegalMoveException();
		}
		
		int stonesToMove = getStonesInPit(pit, player);
		if (stonesToMove == 0) {
			log.debug("Ignoring move from " + player + " silently, tried to move zero stones...");
			throw new IllegalMoveException();
		}
		
		boolean endedInKalaha = false;
		setStonesInPit(0, pit, player);
		
		int skippedKalahas = 0;
		int offset = (player == KalahaPlayer.NORTH) ? NUMBER_OF_PITS + 1 : 0;
		for (int i = pit + 1; i < pit + stonesToMove + 1; i++) {
			int currentPit = (i + skippedKalahas + offset) % state.getHouses().length;
			
			// Skip opponent's kalaha
			if (isOpponentKalaha(player, currentPit)) {
				skippedKalahas++;
				currentPit = (i + skippedKalahas + offset) % state.getHouses().length;
			}
			
			// Steal opponent's stones if last stone lands in one of my empty pits.			
			boolean lastStone = i == (pit + stonesToMove);
			boolean myEmptyPit = player.isMyPit(currentPit) && state.getHouses()[currentPit] == 0;
			boolean myKalaha = player.isMyKalaha(currentPit);
			boolean stole = false;
			
			if (lastStone && myEmptyPit) {				
				stole = stealOpponentsStones(player, currentPit);
				// endedInKalaha = stole;
			} else if (lastStone && myKalaha) {
				endedInKalaha = true;
			}
			
			if (!stole) {
				state.getHouses()[currentPit]++;
			}
		}
		
		updatePlayerToAct(player, endedInKalaha);
		checkGameEnd(player);
	}

	private void updatePlayerToAct(KalahaPlayer playerWhoActed, boolean endedInKalaha) {
		if (!endedInKalaha) {
			playerToAct = getOpponent(playerWhoActed);
		}
		state.setPlayerToAct(getPlayerToActId());
	}

	private boolean playerToAct(KalahaPlayer player) {
		return playerToAct == player;
	}

	private boolean stealOpponentsStones(KalahaPlayer player, int currentPit) {
		boolean stole = false;
		int currentStonesInKalaha = getStonesInKalaha(player);		
		int stonesInOpponentPit = getStonesInOpponentPit(player, currentPit);
		if (stonesInOpponentPit > 0 && rules.allowStealingFromEmptyPit()) {
			setStonesInKalaha(currentStonesInKalaha + 1 + stonesInOpponentPit, player);
			setStonesInPit(0, getOpponentPit(player, currentPit), getOpponent(player));
			stole = true;
		}
		return stole;
	}

	private int getStonesInOpponentPit(KalahaPlayer player, int currentPit) {
		int opponentPit = getOpponentPit(player, currentPit);
		return getStonesInPit(opponentPit, getOpponent(player));
	}

	private int getOpponentPit(KalahaPlayer player, int currentPit) {
		int opponentPit = NUMBER_OF_PITS - player.toLocalPit(currentPit) - 1;
		return opponentPit;
	}
	
	private boolean isOpponentKalaha(KalahaPlayer player, int currentPit) {
		return getOpponent(player).isMyKalaha(currentPit);
	}

	
	private void checkGameEnd(KalahaPlayer player) {
		if (!canPlayerMove(player)) {
			if (playerToAct == player || rules.endGameWhenEitherPlayerRunsOutOfStones()) {
				endTransfer(getOpponent(player));
				gameEnded = true;
			}
		} else if (!canPlayerMove(getOpponent(player))) {
			endTransfer(player);
			gameEnded = true;
		}
	}
	
	public void endTransfer(KalahaPlayer player) {
		if(rules.endGameSweepsBoard()) {
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
	}

	public boolean canPlayerMove(KalahaPlayer player) {
		for (int i = 0; i < 6; i++) {
			if(getStonesInPit(i, player) != 0) {
				return true;
			}
		}
		return false;
	}

	private KalahaPlayer getOpponent(KalahaPlayer player) {
		return (player == KalahaPlayer.SOUTH) ? KalahaPlayer.NORTH : KalahaPlayer.SOUTH;
	}

	public void setStonesInPit(int stones, int pit, KalahaPlayer player) {
		if (player == KalahaPlayer.SOUTH) {
			state.getHouses()[pit] = stones;
		} else {
			state.getHouses()[NUMBER_OF_PITS + pit + 1] = stones;
		}		
	}

	public boolean isGameEnded() {
		return gameEnded;
	}
	
	public boolean isDraw() {
		return getStonesInKalaha(KalahaPlayer.SOUTH) == getStonesInKalaha(KalahaPlayer.NORTH);
	}

	public long getWinningPlayerId() {
		if(getStonesInKalaha(KalahaPlayer.SOUTH) > getStonesInKalaha(KalahaPlayer.NORTH)) {
			return southPlayerId;
		} else if(getStonesInKalaha(KalahaPlayer.SOUTH) < getStonesInKalaha(KalahaPlayer.NORTH)) {
			return northPlayerId;
		} else {
			return -1;
		}
	}

	public KalahaPlayer getPlayerToAct() {
		return playerToAct;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int pit : state.getHouses()) {
			sb.append(pit + ",");
		}
		return sb.toString();
	}

	public void setPlayerToAct(KalahaPlayer player) {
		playerToAct = player;
	}
}
