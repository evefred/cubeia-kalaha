package net.kalaha.web.action;

import java.util.ArrayList;
import java.util.List;

import net.kalaha.data.entities.Game;
import net.kalaha.data.entities.GameStatus;
import net.kalaha.data.entities.User;
import net.kalaha.data.manager.GameManager;
import net.kalaha.web.dto.WebGame;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.inject.Inject;

@UrlBinding("/Home.action")
public class HomeActionBean extends BaseActionBean {
	
	@Inject
	private GameManager gameManager;
	
    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution("/WEB-INF/jsp/home.jsp");
    }
    
    public List<WebGame> getMyGames() {
		List<Game> list = gameManager.getMyGames(getCurrentUser(), GameStatus.ACTIVE);
		List<WebGame> next = new ArrayList<WebGame>(list.size());
		User user = getCurrentUser();
		for (Game g : list) {
			next.add(new WebGame(g, user));
		}
		return next;
    }
}
