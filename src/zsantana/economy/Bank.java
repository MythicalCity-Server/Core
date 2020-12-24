package zsantana.economy;

import java.util.HashMap;
import java.util.Map;

public abstract class Bank<T> {
	
	private final String _CURRENCY_NAME;
	
	protected final Map<String, T> _CURRENCY;
	
	public Bank(String currencyName) {
		this._CURRENCY_NAME = currencyName;
		
		this._CURRENCY = new HashMap<>();
		
		start();
	}
	
	public T register(String key, T value) {
		return this._CURRENCY.put(key, value);
	}
	
	public T remove(String key) {
		return this._CURRENCY.remove(key);
	}
	
	public final String getCurrencyName() {
		return this._CURRENCY_NAME;
	}
	
	public final void restart() {
		stop();
		start();
	}
	
	public boolean start() {
		return loadBank();
	}
	
	public final boolean stop() {
		return unloadBank();
	}
	protected abstract boolean loadBank();
	
	protected abstract boolean unloadBank();
	
	public abstract boolean ammend(String key, T value);
}
