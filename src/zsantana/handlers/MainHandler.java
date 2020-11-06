package zsantana.handlers;

import zsantana.economy.EconomyHandler;

public class MainHandler extends Handler {
	
	private EconomyHandler _economy;

	@Override
	public String init() {
		this._economy = new EconomyHandler();
		
		return this._economy.successfulInit();
	}
	@Override
	protected String disable() {
		return this._economy.stop();
	}
}
