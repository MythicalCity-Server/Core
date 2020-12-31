package zsantana.handlers;

import zsantana.command.Command;
import zsantana.configuration.Configuration;
import zsantana.configuration.ConfigurationHandler;
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
	private ConfigurationHandler _configuration;

	/**
	 * Inits the different handlers
	 */
	@Override
	public void enable() {
		Configuration.setCore(_CORE);
		Command.setCore(_CORE);
		
		this._economy = new EconomyHandler();
		this._customItems = new CustomItemsHandler();
		this._configuration = new ConfigurationHandler();

	}

	/**
	 * Runs stop for the different handlers
	 */
	@Override
	protected void disable() {
		this._economy.stop();
		this._customItems.stop();
		this._configuration.stop();
	}
}
