package zsantana.economy;

import java.util.HashMap;
import java.util.Map;

import zsantana.economy.banks.DollarBank;
import zsantana.handlers.Handler;

public class EconomyHandler extends Handler {
	
	private Map<String, Bank> _banks;

	@Override
	public String init() {
		this._banks = new HashMap<>();
		
		Bank dollar = new DollarBank();
		this._banks.put("DollarBank", dollar);
		
		return !dollar.successfulInit() ? "Dollar failed to load." : "";
	}
	
	@Override
	protected String disable() {
		return "";
	}

}
