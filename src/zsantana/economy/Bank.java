package zsantana.economy;

import java.util.HashMap;
import java.util.Map;

import zsantana.misc.Log;

public abstract class Bank<T> {

	private final String _CURRENCY_NAME, _BANK_NAME;

	protected final Map<String, T> _CURRENCY;

	public Bank(String currencyName, String bankName) {
		this._CURRENCY_NAME = currencyName;
		this._BANK_NAME = bankName;

		this._CURRENCY = new HashMap<>();

		start();
	}

	public T register(String key, T value) {
		return this._CURRENCY.put(key, value);
	}

	public T unregister(String key) {
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
		boolean loaded = load();
		if (loaded) {
			Log.s("[Bank] The " + this._BANK_NAME + " successfully started");
		} else {
			Log.e("[Bank] The " + this._BANK_NAME + " did not successfully start");
		}
		return loaded;
	}

	public final boolean stop() {
		boolean unloaded = unload();
		if (unloaded) {
			Log.s("[Bank] The " + this._BANK_NAME + " successfully stopped");
		} else {
			Log.e("[Bank] The " + this._BANK_NAME + " did not successfully stop");
		}
		return unloaded;
	}

	protected abstract boolean load();

	protected abstract boolean unload();

	public abstract boolean ammend(String key, T value);
}
