package net.kalaha.common.json;

import static net.kalaha.common.util.Strings.fromBytes;
import static net.kalaha.common.util.Strings.toBytes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

public abstract class JsonTransformer implements ActionTransformer {
	
	@Inject
	protected ObjectMapper map;
	
	protected abstract String getActionPackage();
	
	protected ClassLoader getClassLoader() {
		return getClass().getClassLoader();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractAction> T fromString(String json) throws IllegalActionException {
		try {
			JsonNode node = map.readTree(json);
			String pack = getActionPackage();
			String cl = node.get("_action").asText();
			String name = pack + "." + cl;
			Class<?> clazz = getClassLoader().loadClass(name);
			return (T) map.treeToValue(node, clazz);
		} catch (Exception e) {
			throw new IllegalActionException("Failed to deserialize action", e);
		}
	}
	
	public <T extends AbstractAction> String toString(T action) {
		try {
			return map.writeValueAsString(action);
		} catch (Exception e) {
			throw new IllegalActionException("Failed to serialize action", e);
		}
	}

	public <T extends AbstractAction> byte[] toUTF8Data(T action) {
		String s = toString(action);
		return toBytes(s);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractAction> T fromUTF8Data(byte[] arr) throws IllegalActionException {
		String s = fromBytes(arr);
		return (T) fromString(s);
	}
}
