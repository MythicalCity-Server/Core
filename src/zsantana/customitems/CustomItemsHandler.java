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

	private CustomItemEventHandler _eventHandler;

	@Override
	protected void enable() {
		this._eventHandler = new CustomItemEventHandler();
		Bukkit.getPluginManager().registerEvents(this._eventHandler, _CORE);
	}

	@Override
	protected void disable() {
		this._eventHandler = null;
	}
}
