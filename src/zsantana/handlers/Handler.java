package zsantana.handlers;

import zsantana.Core;

/**
 * The superclass to all handlers to standardize their platform and provide an
 * opportunity to each to evolve
 * 
 * @author Zackary Santana
 *
 */
public abstract class Handler {
	
	protected static Core _CORE;
	
	/**
	 * @param core Hooks in to the core to allow for any hooks to be accessed
	 */
	public static void setCore(Core core) {
		_CORE = core;
	}
	
	public Handler() {
		enable();
	}
	
	/**
	 * Restarts the handler
	 */
	public final void restart() {
		stop();
		enable();
	}
	
	/**
	 * Stops the handler
	 */
	public final void stop() {
		disable();
	}

	/**
	 * Enables the handler
	 */
	protected abstract void enable();
	
	/**
	 * Disables the handler
	 */
	protected abstract void disable();
	
}
