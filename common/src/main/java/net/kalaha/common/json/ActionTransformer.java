package net.kalaha.common.json;

public interface ActionTransformer {

	public <T extends AbstractAction> T fromString(String str);
	
	public <T extends AbstractAction> String toString(T action);

	public <T extends AbstractAction> byte[] toUTF8Data(T action);
	
	public <T extends AbstractAction> T fromUTF8Data(byte[] arr) throws IllegalActionException;
	
}