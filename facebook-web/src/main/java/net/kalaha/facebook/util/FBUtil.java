package net.kalaha.facebook.util;

import java.util.Collections;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

public class FBUtil {

	private FBUtil() { }
	
	public static void setOrReplaceAttribute(MarkupContainer parent, String compId, String attribute, String value) {
		setOrReplaceAttributes(parent, compId, Collections.singletonMap(attribute, value));
	}

	public static void setOrReplaceAttributes(MarkupContainer parent, String compId, Map<String, String> atts) {
		WebMarkupContainer c = new WebMarkupContainer(compId);
		for (String key : atts.keySet()) {
			c.add(new AttributeModifier(key, true, Model.of(atts.get(key))));
		}
		parent.add(c);
	}
}
