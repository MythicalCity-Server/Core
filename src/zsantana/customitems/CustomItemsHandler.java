package zsantana.customitems;

import org.bukkit.Bukkit;

import zsantana.handlers.Handler;

/**
 * A skeleton handler class that just manages registering the event listener
 * 
 * @author Zackary Santana
 *
 */
public class CustomItemsHandler extends Handler {
	
	private final EventHandler _eventHandler;
	
	/**
	 * Creates and registers the event listener
	 */
	public CustomItemsHandler() {
		this._eventHandler = new EventHandler();
	}
	
	@Override
	protected void enable() {
		Bukkit.getPluginManager().registerEvents(this._eventHandler, _CORE);
	}

	@Override
	protected void disable() {
	}
}
