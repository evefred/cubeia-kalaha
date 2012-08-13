package net.kalaha.game;

import static com.cubeia.firebase.api.game.lobby.DefaultTableAttributes._LAST_MODIFIED;
import static com.cubeia.firebase.api.game.lobby.DefaultTableAttributes._SEATED;

import java.util.Map;

import net.kalaha.common.util.SystemTime;

import org.joda.time.DateTime;

import com.cubeia.firebase.api.common.AttributeValue;
import com.cubeia.firebase.api.game.lobby.LobbyTableFilter;

public class ActivatorTableFilter implements LobbyTableFilter {

	private final SystemTime time;
	private final long maxAge;

	public ActivatorTableFilter(SystemTime time, long maxAge) {
		this.time = time;
		this.maxAge = maxAge;
	}
	
	@Override
	public boolean accept(Map<String, AttributeValue> atts) {
		if(isOld(atts) && noSeated(atts)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// --- PRIVATE METHODS --- //

	private boolean isOld(Map<String, AttributeValue> atts) {
		AttributeValue val = atts.get(_LAST_MODIFIED.name());
		DateTime threshold = new DateTime(time.utc() - maxAge);
		DateTime lastModified = new DateTime(Long.parseLong(val.getData().toString()));
		return lastModified.isBefore(threshold);
	}

	private boolean noSeated(Map<String, AttributeValue> atts) {
		AttributeValue val = atts.get(_SEATED.name());
		return val.getIntValue() == 0;
	}
}
