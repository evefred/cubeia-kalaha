package net.kalaha.game;

import org.apache.log4j.Logger;

import com.cubeia.firebase.api.game.activator.ActivatorContext;
import com.cubeia.firebase.api.game.activator.DefaultActivator;
import com.cubeia.firebase.api.game.activator.DefaultCreationParticipant;
import com.cubeia.firebase.api.game.lobby.LobbyTableAttributeAccessor;
import com.cubeia.firebase.api.game.table.Table;
import com.cubeia.firebase.api.server.SystemException;

public class ActivatorImpl extends DefaultActivator {
	
	private final Logger log = Logger.getLogger(getClass());
	
	public ActivatorImpl() {
		setCreationParticipant(new DefaultCreationParticipant() {
			
			@Override
			public void tableCreated(Table table, LobbyTableAttributeAccessor acc) {
				log.debug("Setting state of newly created table: " + table.getId());
				table.getGameState().setState(new net.kalaha.game.action.State());
				super.tableCreated(table, acc);
			}		
		});
	}
	
	@Override
	public void init(ActivatorContext context) throws SystemException {
		super.init(context);
		/*
		 * This is a hack for now, we'll force the number of seats to 2...
		 */
		super.getConfiguration().setSeats(2);
	}
}


