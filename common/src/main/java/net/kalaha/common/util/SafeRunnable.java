package net.kalaha.common.util;

import org.apache.log4j.Logger;

public abstract class SafeRunnable implements Runnable {

	@Override
	public final void run() {
		try {
			execute();
		} catch(Throwable e) {
			Logger.getLogger(getClass()).error("Unexpected exception", e);
		}
	}

	protected abstract void execute();

}
