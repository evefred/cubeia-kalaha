package net.kalaha.table.impl;

import static org.testng.Assert.assertEquals;

import java.util.Collection;

import net.kalaha.table.api.TableQuery;

import org.testng.annotations.Test;

import com.cubeia.firebase.api.action.GameAction;
import com.cubeia.firebase.api.action.mtt.MttAction;
import com.cubeia.firebase.api.action.service.ClientServiceAction;
import com.cubeia.firebase.api.action.service.ServiceAction;
import com.cubeia.firebase.api.routing.ActivatorAction;
import com.cubeia.firebase.api.service.NoSuchRouteException;
import com.cubeia.firebase.api.service.ServiceDiscriminator;
import com.cubeia.firebase.api.service.ServiceRouter;

public class TableServiceTest {

	@Test
	public void testRoundTrip() throws Exception {
		Router r = new Router();
		TableService s = new TableService();
		s.setRouter(r);
		String req1 = "{\"gameId\":666}";
		ClientServiceAction act1 = new ClientServiceAction(1, 2, req1.getBytes("UTF-8"));
		s.onAction(act1);
		TableQuery q = (TableQuery) r.toActivator.getData();
		assertEquals(q.correlationId, 2);
		assertEquals(q.gameId, 666);
		assertEquals(q.userId, 1);
		s.tableLocated(q, 8);
		String resp = new String(r.toPlayer.getData(), "UTF-8");
		assertEquals(r.toPlayer.getPlayerId(), 1);
		assertEquals(r.toPlayer.getSeq(), 2);
		assertEquals(resp, "{\"gameId\":666,\"tableId\":8}");
	}
	
	private static class Router implements ServiceRouter {

		private ActivatorAction<?> toActivator;
		private ServiceAction toPlayer;

		
		@Override
		public void dispatchToPlayer(int arg0, Collection<? extends GameAction> arg1) { }
		
		@Override
		public void dispatchToGame(int arg0, GameAction arg1) { }

		@Override
		public void dispatchToGameActivator(int arg0, ActivatorAction<?> arg1) {
			this.toActivator = arg1; 
		}

		@Override
		public void dispatchToPlayer(int arg0, ServiceAction arg1) {
			this.toPlayer = arg1; 
		}

		@Override
		public void dispatchToPlayers(int[] arg0, ServiceAction arg1) { }

		@Override
		public void dispatchToService(ServiceDiscriminator arg0, ServiceAction arg1) throws NoSuchRouteException { }

		@Override
		public void dispatchToTournament(int arg0, MttAction arg1) { }

		@Override
		public void dispatchToTournamentActivator(int arg0, ActivatorAction<?> arg1) { }

		@Override
		public void dispatchToPlayer(int arg0, GameAction arg1) { }
		
	}
}
