package zsantana.handlers;

import zsantana.customitems.CustomItemsHandler;
import zsantana.economy.EconomyHandler;

/**
 * Handles all the handlers for the different components of the plugin
 * 
 * @author Zackary Santana
 *
 */
@SuppressWarnings("unused")
public class MainHandler extends Handler {

	private EconomyHandler _economy;
	private CustomItemsHandler _customItems;

	/**
	 * Inits the different handlers
	 */
	@Override
	public void enable() {
		this._economy = new EconomyHandler();
		this._customItems = new CustomItemsHandler();

	}

	/**
	 * Runs stop for the different handlers
	 */
	@Override
	protected void disable() {
		this._economy.stop();
		this._customItems.stop();
	}
}
