package zsantana;

import org.bukkit.plugin.java.JavaPlugin;

import zsantana.handlers.Handler;
import zsantana.handlers.MainHandler;

/**
 * The starting location and starting point of all the plugin
 * 
 * @author Zackary Santana
 *
 */
public class Core extends JavaPlugin {

	private MainHandler _handler;

	/**
	 * Hooks in to Handler then creates an instance, then creates a test class in
	 * case there is any tests to be done
	 */
	@Override
	public void onEnable() {
		Handler.setCore(this);

		this._handler = new MainHandler();

		// Testing:
		// new Testing();
	}

	/**
	 * Runs the stop of all handlers through the main handler
	 */
	@Override
	public void onDisable() {
		this._handler.stop();
	}
}