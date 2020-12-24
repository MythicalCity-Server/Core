package zsantana.handlers;

import zsantana.customitems.CustomItemsHandler;
import zsantana.economy.EconomyHandler;

@SuppressWarnings("unused")
public class MainHandler extends Handler {
	
	private EconomyHandler _economy;
	private CustomItemsHandler _customItems;

	@Override
	public void enable() {
		this._economy = new EconomyHandler();
		this._customItems = new CustomItemsHandler();
		
	}
	@Override
	protected void disable() {
		this._economy.stop();
	}
}
