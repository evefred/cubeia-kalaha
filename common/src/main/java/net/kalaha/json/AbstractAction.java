package net.kalaha.json;

public abstract class AbstractAction {
	
	private String _action;
	
	protected AbstractAction() {
		this._action = getClass().getSimpleName();
	}
	
	public String get_action() {
		return _action;
	}
	
	public void set_action(String name) {
		this._action = name;
	}
}
