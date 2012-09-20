package net.kalaha.common.json;

import static net.kalaha.common.util.Strings.fromBytes;
import static net.kalaha.common.util.Strings.toBytes;
import net.sf.json.JSONObject;

public abstract class JsonTransformer implements ActionTransformer {
	
	protected abstract String getActionPackage();
	
	@SuppressWarnings("unchecked")
	public <T extends AbstractAction> T fromString(String json) throws IllegalActionException {
		// JSONArray arr = JSONArray.fromObject(json);
		JSONObject o = JSONObject.fromObject(json);
		String pack = getActionPackage();
		String cl = o.getString("_action");
		try {
			String name = pack + "." + cl;
			Class<?> clazz = JsonTransformer.class.getClassLoader().loadClass(name);
			// JSONObject o = arr.getJSONObject(1);
			return (T) JSONObject.toBean(o, clazz);
		} catch (ClassNotFoundException e) {
			throw new IllegalActionException("Action '" + cl + "' not found in package '" + pack + "'");
		}
	}
	
	public <T extends AbstractAction> String toString(T action) {
		/*String cl = action.getClass().getSimpleName();
		List<Object> tmp = new ArrayList<Object>(2);
		tmp.add(cl);
		tmp.add(JSONObject.fromObject(action));*/
		return JSONObject.fromObject(action).toString();
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
