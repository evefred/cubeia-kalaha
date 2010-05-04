package net.kalaha.game.json;

import net.kalaha.game.action.IllegalActionException;


public interface ActionTransformer {

	public Object fromString(String str);
	
	public String toString(Object action);

	public byte[] toUTF8Data(Object action);
	
	public Object fromUTF8Data(byte[] arr) throws IllegalActionException;
	
}