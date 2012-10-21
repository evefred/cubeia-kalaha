package net.kalaha.data.manager;

import static net.kalaha.common.elo.EloCalculator.Result.DRAW;
import static net.kalaha.common.elo.EloCalculator.Result.PLAYER_ONE;
import static net.kalaha.common.elo.EloCalculator.Result.PLAYER_TWO;
import static net.kalaha.data.entities.GameResult.TIMEOUT;
import static net.kalaha.data.entities.GameStatus.ACTIVE;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.kalaha.common.elo.EloCalculator;
import net.kalaha.common.elo.EloCalculator.Matchup;
import net.kalaha.common.elo.EloCalculator.Result;
import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameResult;
import net.kalaha.data.entities.GameStats;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.GameType;
import net.kalaha.data.entities.User;

import org.joda.time.Duration;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
 
@Singleton
public class GameManagerImpl implements GameManager {

	@Inject
	private Provider<EntityManager> em;
	
	@Inject
	private SystemTime time;
	
	@Inject
	private EloCalculator eloCalculator;
	
	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Game> reapGames(int maxAgeDays) {
		long last = time.utc() - daysToMillis(maxAgeDays);
		Query q = em.get().createQuery("select g from Game g where g.lastModified < :last and g.status = :status");
		q.setParameter("last", last);
		q.setParameter("status", ACTIVE);
		List<Game> games = (List<Game>) q.getResultList();
		for (Game g : games) {
			User winner = (g.isOwnersMove() ? g.getOpponent() : g.getOwner());
			finishGame(g.getId(), winner, TIMEOUT);
		}
		return games;
	}

	@Override
	@Transactional
	public Game createGame(GameType type, User owner, User opponent, long moveTimeout, int[] initState) {
		long now = time.utc();
		Game g = new Game();
		g.setCreated(now);
		g.setLastModified(now);
		g.setMoveTimeout(moveTimeout);
		g.setOwner(owner);
		g.setOpponent(opponent);
		g.setOwnersMove(true);
		g.setStatus(GameStatus.ACTIVE);
		g.setType(type);
		if(initState != null) {
			g.updateGameState(initState);
		}
		em.get().persist(g);
		return g;
	}
	
	@Override
	@Transactional
	public Game getGame(long gameId) {
		return em.get().find(Game.class, gameId);
	}
	
	@Override
	@Transactional
	public Game finishGame(long gameId, User winner, GameResult result) {
		Game g = getGame(gameId);
		if(g == null) throw new IllegalArgumentException("No such game: " + gameId);
		g.setLastModified(System.currentTimeMillis());
		g.setStatus(GameStatus.FINISHED);
		if(winner != null) {
			g.setWinningUser(winner.getId());
		}
		g.setResult(result);
		updateStats(g, winner, result);
		return g;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Game> getMyGames(User user, GameStatus status) {
		String tmp = "select g from Game g where (g.owner = :own or g.opponent = :opp)";
		if(status != null) {
			tmp += " and g.status = :status";
		}
		Query q = em.get().createQuery(tmp);
		q.setParameter("own", user);
		q.setParameter("opp", user);
		if(status != null) {
			q.setParameter("status", status);
		}
		return q.getResultList();
	}
	
	
	// -- PRIVATE METHODS --- //
	
	private void updateStats(Game g, User winner, GameResult res) {
		GameStats one = g.getOwner().getGameStats();
		GameStats two = g.getOpponent().getGameStats();
		Result result = (winner == null ? DRAW : (winner.getId() == g.getOwner().getId() ? PLAYER_ONE : PLAYER_TWO));
		Matchup m = eloCalculator.calculate(result, new Matchup(one.getEloRating(), two.getEloRating()));
		one.setEloRating(m.playerOne);
		two.setEloRating(m.playerTwo);
		one.incrementTotalGames();
		two.incrementTotalGames();
		if(result == DRAW) {
			one.incrementGamesDrawn();
			two.incrementGamesDrawn();
		} else if(winner.getId() == g.getOwner().getId()) {
			one.incrementGamesWon();
			two.incrementGamesLost();
		} else {
			two.incrementGamesWon();
			one.incrementGamesLost();
		}
	}
	
	private long daysToMillis(int maxAgeDays) {
		return Duration.standardDays(maxAgeDays).getMillis();
	}
}
