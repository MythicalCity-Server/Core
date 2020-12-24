package zsantana;

import org.bukkit.plugin.java.JavaPlugin;

import zsantana.handlers.Handler;
import zsantana.handlers.MainHandler;

public class Core extends JavaPlugin {
	
	private MainHandler _handler;

	@Override
	public void onEnable() {
		Handler.setCore(this);
		
		this._handler = new MainHandler();
	}

	@Override
	public void onDisable() {
		this._handler.stop();
	}
}