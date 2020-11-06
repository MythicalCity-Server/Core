package zsantana.economy;

import java.util.HashMap;
import java.util.Map;

public abstract class Bank {
	
	private final String _CURRENCY_NAME;
	private final boolean _SUCCESSFUL_INIT;
	
	protected final Map<String, Double> _CURRENCY;
	
	public Bank(String currencyName) {
		this._CURRENCY_NAME = currencyName;
		
		this._CURRENCY = new HashMap<>();
		
		this._SUCCESSFUL_INIT = loadBank();
	}
	
	public final String getCurrencyName() {
		return this._CURRENCY_NAME;
	}
	
	public final boolean successfulInit() {
		return this._SUCCESSFUL_INIT;
	}
	
	protected abstract boolean loadBank();
}
