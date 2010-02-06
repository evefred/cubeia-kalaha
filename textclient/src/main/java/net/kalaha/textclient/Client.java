package net.kalaha.textclient;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

import net.kalaha.game.action.IllegalActionException;
import net.kalaha.game.action.Sow;
import net.kalaha.game.json.JsonTransformer;

import com.cubeia.firebase.clients.java.connector.text.SimpleTextClient;

public class Client extends SimpleTextClient {

	public Client(String host, int port) {
		super(host, port);
		Handler h = new Handler();
		addPacketHandler(h);
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
	    	   buf = ByteBuffer.wrap(JsonTransformer.toUTF8Data(s));
	       } else {
	    	   throw new IllegalActionException("No such action: " + action);
	       }
	       
	       context.getConnector().sendDataPacket(tableid, context.getPlayerId(), buf);
				
	    } catch (Exception e) {
	       reportBadCommand(e.toString());
	    }
	}

	private void reportBadCommand(String error) {
	    System.err.println("Invalid command ("+error+") Format: TID cmd");
	}

}

