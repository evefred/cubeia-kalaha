package net.kalaha.textclient;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import net.kalaha.game.action.IllegalActionException;
import net.kalaha.game.action.Sow;
import net.kalaha.game.json.JsonTransformer;
import net.kalaha.table.api.TableManager;

import com.cubeia.firebase.api.util.ParameterUtil;
import com.cubeia.firebase.clients.java.connector.text.CommandNotifier;
import com.cubeia.firebase.clients.java.connector.text.SimpleTextClient;
import com.cubeia.firebase.io.protocol.CreateTableRequestPacket;
import com.cubeia.firebase.io.protocol.Enums;
import com.cubeia.firebase.io.protocol.Param;
import com.cubeia.firebase.io.protocol.ServiceTransportPacket;

public class Client extends SimpleTextClient {

	public Client(String host, int port) {
		super(host, port);
		Handler h = new Handler();
		addPacketHandler(h);
		super.commandNotifier = new CommandNotifier(context, this, true) {
			
			@Override
			public void handleCommand(String command) {
				if (command.startsWith("table ")) {
					String[] args = command.split(" ");
				    String json = "{\"gameId\":" + args[1] + "}";
				    ServiceTransportPacket p = new ServiceTransportPacket();
				    p.idtype = (byte) Enums.ServiceIdentifier.CONTRACT.ordinal();
				    p.pid = context.getPlayerId();
				    p.seq = 1;
				    p.service = TableManager.class.getName();
				    p.servicedata = toUTF8Data(json);
				    context.getConnector().sendPacket(p);
				} else {
					super.handleCommand(command);
				}
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
	    	   buf = ByteBuffer.wrap(new JsonTransformer().toUTF8Data(s));
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
	
	public byte[] toUTF8Data(String s) {
		try {
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
	
	public String fromUTF8Data(byte[] arr) {
		try {
			return new String(arr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}

	private void reportBadCommand(String error) {
	    System.err.println("Invalid command ("+error+") Format: TID cmd");
	}

}

