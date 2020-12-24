package zsantana.handlers;

import zsantana.Core;

public abstract class Handler {
	
	protected static Core _CORE;
	
	public static void setCore(Core core) {
		_CORE = core;
	}
	
	
	public Handler() {
		enable();
	}
	
	public final void restart() {
		stop();
		enable();
	}
	
	public final void stop() {
		disable();
	}

	protected abstract void enable();
	
	protected abstract void disable();
	
}
