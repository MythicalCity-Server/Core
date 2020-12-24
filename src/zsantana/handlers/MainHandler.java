package zsantana.handlers;

import zsantana.economy.EconomyHandler;

public class MainHandler extends Handler {
	
	private EconomyHandler _economy;

	@Override
	public void enable() {
		this._economy = new EconomyHandler();
		
	}
	@Override
	protected void disable() {
		this._economy.stop();
	}
}
