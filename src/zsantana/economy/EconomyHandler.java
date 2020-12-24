package zsantana.economy;

import java.util.HashMap;
import java.util.Map;

import zsantana.economy.banks.DollarBank;
import zsantana.handlers.Handler;

/**
 * Handles the economy i.e. the banks and whatnot
 * 
 * @author Zackary Santana
 *
 */
public class EconomyHandler extends Handler {
	
	private Map<String, Bank<?>> _banks;

	@Override
	public void enable() {
		this._banks = new HashMap<>();
		
		this._banks.put("dollar_bank", new DollarBank());
	}
	
	/**
	 * Gets a bank with the name designated
	 * 
	 * @param name The name, specifically the key designated to the bank
	 * @return The bank that is requested with that name
	 */
	public Bank<?> getBank(String name) {
		return this._banks.get(name);
	}
	
	@Override
	protected void disable() {
		this._banks.values().forEach((bank) -> {
			bank.unload();
		});
	}
}
