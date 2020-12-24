package zsantana.customitems;

import org.bukkit.Bukkit;

import zsantana.handlers.Handler;

public class CustomItemsHandler extends Handler {
	
	private final EventHandler _eventHandler;
	
	public CustomItemsHandler() {
		this._eventHandler = new EventHandler(this);
		
		Bukkit.getPluginManager().registerEvents(this._eventHandler, _CORE);
	}
	
	@Override
	protected void enable() {
		
	}

	@Override
	protected void disable() {
		
	}
}
