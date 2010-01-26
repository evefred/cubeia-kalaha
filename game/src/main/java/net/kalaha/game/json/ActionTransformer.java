package net.kalaha.game.json;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.kalaha.game.action.IllegalActionException;
import net.kalaha.game.action.Sow;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ActionTransformer {

	private ActionTransformer() { }
	
	public static Object fromString(String json) throws IllegalActionException {
		JSONArray arr = JSONArray.fromObject(json);
		String pack = Sow.class.getPackage().getName();
		String cl = arr.getString(0);
		try {
			String name = pack + "." + cl;
			Class<?> clazz = ActionTransformer.class.getClassLoader().loadClass(name);
			JSONObject o = arr.getJSONObject(1);
			return JSONObject.toBean(o, clazz);
		} catch (ClassNotFoundException e) {
			throw new IllegalActionException("Action '" + cl + "' not found in package '" + pack + "'");
		}
	}
	
	public static String toString(Object action) {
		String cl = action.getClass().getSimpleName();
		List<Object> tmp = new ArrayList<Object>(2);
		tmp.add(cl);
		tmp.add(JSONObject.fromObject(action));
		return JSONArray.fromObject(tmp).toString();
	}

	public static byte[] toUTF8Data(Object action) {
		String s = toString(action);
		try {
			return s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
	
	public static Object fromUTF8Data(byte[] arr) throws IllegalActionException {
		try {
			String s = new String(arr, "UTF-8");
			return fromString(s);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Missing UTF-8?!");
		}
	}
}
