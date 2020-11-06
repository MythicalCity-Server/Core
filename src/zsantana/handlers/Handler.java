package zsantana.handlers;

import zsantana.Core;

public abstract class Handler {
	
	protected static Core _CORE;
	
	public static void setCore(Core core) {
		_CORE = core;
	}
	
	private final String _INIT_SUCCESSFUL;
	
	public Handler() {
		this._INIT_SUCCESSFUL = init();
	}
	
	public final String restart() {
		return stop() + init();
	}
	
	public final String stop() {
		return disable();
	}
	
	public final String successfulInit() {
		return this._INIT_SUCCESSFUL;
	}

	protected abstract String init();
	
	protected abstract String disable();
	
}
