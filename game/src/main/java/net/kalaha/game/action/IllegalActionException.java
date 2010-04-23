package net.kalaha.game.action;

public class IllegalActionException extends IllegalStateException {

	private static final long serialVersionUID = 3673062762099550191L;

	public IllegalActionException() { }

	public IllegalActionException(String s) {
		super(s);
	}

	public IllegalActionException(Throwable cause) {
		super(cause);
	}

	public IllegalActionException(String message, Throwable cause) {
		super(message, cause);
	}
}
