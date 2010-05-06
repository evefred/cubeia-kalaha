package net.kalaha.data.manager;

import java.util.List;

import javax.persistence.Query;

import net.kalaha.entities.Game;
import net.kalaha.entities.GameForm;
import net.kalaha.entities.GameResult;
import net.kalaha.entities.GameStatus;
import net.kalaha.entities.GameType;
import net.kalaha.entities.User;

import org.apache.log4j.Logger;

import com.cubeia.firebase.guice.inject.Log4j;
import com.google.inject.Inject;
import com.google.inject.Singleton;
 
@Singleton
public class GameManagerImpl implements GameManager {
	
	@Inject
	private Transactions trans;

	@Log4j
	private Logger log;

	@Override
	public Game createGame(GameType type, GameForm form, User owner, User opponent, long moveTimeout, int[] initState) {
		trans.enter();
		try {
			long now = System.currentTimeMillis();
			Game g = new Game();
			g.setCreated(now);
			g.setForm(form);
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
			trans.current().persist(g);
			trans.commit();
			return g;
		} catch(Exception e) {
			log.error("Failed transaction", e);
			trans.rollback();
			return null;
		} finally {
			trans.exit();
		}
	}
	
	/*@Override
	public void updateState(int gameId, int[] state) {
		trans.enter();
		try {
			getGame(gameId).updateGameState(state);
			trans.commit();
		} catch(Exception e) {
			log.error("Failed transaction", e);
			trans.rollback();
		} finally {
			trans.exit();
		}
	}*/

	@Override
	public Game getGame(int gameId) {
		trans.enter();
		try {
			return trans.current().find(Game.class, gameId);
		} finally {
			trans.exit();
		}
	}
	
	@Override
	public Game finishGame(int gameId, User winner) {
		trans.enter();
		try {
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
			trans.commit();
			return g;
		} catch(Exception e) {
			log.error("Failed transaction", e);
			trans.rollback();
			return null;
		} finally {
			trans.exit();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Game> getMyGames(User user, GameStatus status, GameForm form) {
		trans.enter();
		try {
			String tmp = "select g from Game g where (g.owner = :own or g.opponent = :opp)";
			if(status != null) {
				tmp += " and g.status = :status";
			}
			if(form != null) {
				tmp += " and g.form = :form";
			}
			Query q = trans.current().createQuery(tmp);
			q.setParameter("own", user);
			q.setParameter("opp", user);
			if(status != null) {
				q.setParameter("status", status);
			}
			if(form != null) {
				q.setParameter("form", form);
			}
			return q.getResultList();
		} finally {
			trans.exit();
		}
	}
}
