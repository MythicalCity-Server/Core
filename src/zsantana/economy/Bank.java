package zsantana.economy;

import java.util.HashMap;
import java.util.Map;

import zsantana.misc.Log;

/**
 * A class that represents a storage system based on keys and values
 * 
 * @author Zackary Santana
 *
 * @param <T> The type of bank, usually double
 */
public abstract class Bank<T> {

	private final String _CURRENCY_NAME, _BANK_NAME;

	protected final Map<String, T> _CURRENCY;

	/**
	 * Defines the currency name and defines the banks name
	 * 
	 * @param currencyName The name of the currency, i.e. dollars
	 * @param bankName     The name of the bank, i.e. Bank of America
	 */
	public Bank(String currencyName, String bankName) {
		this._CURRENCY_NAME = currencyName;
		this._BANK_NAME = bankName;

		this._CURRENCY = new HashMap<>();

		start();
	}

	/**
	 * Registers an account with this bank with a value
	 * 
	 * @param key   The account's key to register
	 * @param value The value of the account
	 * @return A previous account's value if it existed
	 */
	public T register(String key, T value) {
		return this._CURRENCY.put(key, value);
	}

	/**
	 * Unregisters an account with this bank
	 * 
	 * @param key The account's key to unregister
	 * @return The account that was unregistered
	 */
	public T unregister(String key) {
		return this._CURRENCY.remove(key);
	}

	/**
	 * @return The name of the currency
	 */
	public final String getCurrencyName() {
		return this._CURRENCY_NAME;
	}

	/**
	 * Restarts this bank
	 */
	public final void restart() {
		stop();
		start();
	}

	/**
	 * Starts this bank
	 * 
	 * @return If it was successful
	 */
	public boolean start() {
		boolean loaded = load();
		if (loaded) {
			Log.s("[Bank] The " + this._BANK_NAME + " successfully started");
		} else {
			Log.e("[Bank] The " + this._BANK_NAME + " did not successfully start");
		}
		return loaded;
	}

	/**
	 * Stops this bank
	 * 
	 * @return If it was successful
	 */
	public final boolean stop() {
		boolean unloaded = unload();
		if (unloaded) {
			Log.s("[Bank] The " + this._BANK_NAME + " successfully stopped");
		} else {
			Log.e("[Bank] The " + this._BANK_NAME + " did not successfully stop");
		}
		return unloaded;
	}

	/**
	 * @return If it successfully loaded what it needed to
	 */
	protected abstract boolean load();

	/**
	 * @return If it successfully unloaded what it needed to
	 */
	protected abstract boolean unload();

	/**
	 * Changes the value for an account with the key
	 * 
	 * @param key   The key of the account you want to change
	 * @param value The value you want to amend to the account
	 * @return If this action was successful
	 */
	public abstract boolean amend(String key, T value);
}
