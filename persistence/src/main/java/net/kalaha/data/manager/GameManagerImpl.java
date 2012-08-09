package net.kalaha.data.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.kalaha.common.util.SystemTime;
import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameResult;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.GameType;
import net.kalaha.data.entities.User;

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
	public Game getGame(int gameId) {
		return em.get().find(Game.class, gameId);
	}
	
	@Override
	@Transactional
	public Game finishGame(int gameId, User winner) {
		Game g = getGame(gameId);
		if(g == null) throw new IllegalArgumentException("No such game: " + gameId);
		g.setLastModified(System.currentTimeMillis());
		g.setStatus(GameStatus.FINISHED);
		if(winner != null) {
			g.setWinningUser(winner.getId());
			g.setResult(GameResult.WIN);
		} else {
			g.setResult(GameResult.DRAW);
		}
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
}
