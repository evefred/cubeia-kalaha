package net.kalaha.textclient;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

import net.kalaha.common.json.IllegalActionException;
import net.kalaha.game.action.Sow;
import net.kalaha.game.action.Transformer;
import net.kalaha.table.api.CreateGameRequest;
import net.kalaha.table.api.GetTableRequest;
import net.kalaha.table.api.TableManager;
import net.kalaha.table.api.TableRequestAction;

import com.cubeia.firebase.clients.java.connector.text.CommandNotifier;
import com.cubeia.firebase.clients.java.connector.text.SimpleTextClient;
import com.cubeia.firebase.io.protocol.Enums;
import com.cubeia.firebase.io.protocol.ServiceTransportPacket;

public class Client extends SimpleTextClient {
	
	public Client(String host, int port) {
		super(host, port);
		Handler h = new Handler();
		addPacketHandler(h);
		super.commandNotifier = new CommandNotifier(context, this, true) {
			
			private net.kalaha.table.impl.Transformer serviceTrans = new net.kalaha.table.impl.Transformer();
			
			@Override
			public void handleCommand(String command) {
				if (command.startsWith("gt ")) {
					String[] args = command.split(" ");
				    GetTableRequest req = new GetTableRequest(context.getPlayerId(), 1, Integer.parseInt(args[1]));
					sendRequest(req);
				} else 	if (command.startsWith("cg ")) {
					String[] args = command.split(" ");
					CreateGameRequest req = new CreateGameRequest(context.getPlayerId(), Integer.parseInt(args[1]), 1);
				    sendRequest(req);
				} else {
					super.handleCommand(command);
				}
			}

			private void sendRequest(TableRequestAction req) {
				ServiceTransportPacket p = new ServiceTransportPacket();
				p.idtype = (byte) Enums.ServiceIdentifier.CONTRACT.ordinal();
				p.pid = context.getPlayerId();
				p.service = TableManager.class.getName();
				p.servicedata = serviceTrans.toUTF8Data(req);
				p.seq = 1;
				context.getConnector().sendPacket(p);
			}
		};
	}

	public static void main(String[] args) {
	    if (args.length < 1) {
	    	System.err.println("Usage: java Client [port] host \nEx.: " +
            		"\n\t java Client localhost" +
            		"\n\t java Client 4123 localhost");
            return;
        }
		
        int hostIndex = 0;
        int port = 4123; // Default
        
        if (Pattern.matches("[0-9]+", args[0])) {
            port = Integer.parseInt(args[0]);            
            hostIndex = 1;
        }
       
        Client client = new Client(args[hostIndex], port);
        client.run();
	
	}
	
	@Override
	public void handleCommand(String command) {
	    try {
	       String[] args = command.split(" ");

	       ByteBuffer buf = null; 
	       int tableid = Integer.parseInt(args[0]);
	       String action = args[1];
	       
	       if(action.equals("sow")) {
	    	   Sow s = new Sow();
	    	   s.setHouse(Integer.parseInt(args[2]));
	    	   s.setPlayerId(context.getPlayerId());
	    	   buf = ByteBuffer.wrap(new Transformer().toUTF8Data(s));
	       } else {
	    	   throw new IllegalActionException("No such action: " + action);
	       }
	       
	       if(buf != null) {
	    	   context.getConnector().sendDataPacket(tableid, context.getPlayerId(), buf);
	       }
	       
	    } catch (Exception e) {
	       reportBadCommand(e.toString());
	    }
	}

	private void reportBadCommand(String error) {
	    System.err.println("Invalid command ("+error+") Format: TID cmd");
	}

}

