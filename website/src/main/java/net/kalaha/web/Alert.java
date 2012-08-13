package net.kalaha.web;

public class Alert {
	
	public enum Type {
		
		SUCCESS("Success", "alert-success"),
		INFO("Information", "alert-info"),
		WARNING("Warning", "alert-error"),
		ERROR("Error", "alert-error");
		
		private final String label;
		private final String clazz;
		
		private Type(String label, String clazz) {
			this.label = label;
			this.clazz = clazz;
		}
		
		public String getClazz() {
			return clazz;
		}
		
		public String getLabel() {
			return label;
		}
	}
	
	public final Type type;
	public final String msg;

	public Alert(Type type, String msg) {
		this.type = type;
		this.msg = msg;	
	}
}
