package net.kalaha.web.action;

import net.kalaha.data.entities.Game;
import net.kalaha.data.manager.GameManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.inject.Inject;
import com.google.inject.name.Named;

@UrlBinding("/Game.action")
public class GameActionBean extends BaseActionBean {
	
	private int gameId; // via request parameters
	
	@Inject
	private GameManager gameManager;
	
    @Inject
    @Named("firebase-host")
    protected String firebaseHost;
    
    @Inject
    @Named("firebase-port")
    protected int firebasePort;
	
    @DefaultHandler
    public Resolution view() {
    	Game game = gameManager.getGame(gameId);
    	if(game == null) {
    		log.debug("No game found fopr game ID " + gameId + "; redirecting to home");
    		return new RedirectResolution("/Home.action");
    	} else {
    		return new ForwardResolution("/WEB-INF/jsp/game.jsp");
    	}
    }
    
    public int getGameId() {
		return gameId;
	}
    
    public void setGameId(int gameId) {
		this.gameId = gameId;
	}
    
    public String getFirebaseHost() {
		return firebaseHost;
	}
    
    public int getFirebasePort() {
		return firebasePort;
	}
}
