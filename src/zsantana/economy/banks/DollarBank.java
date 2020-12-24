package zsantana.economy.banks;

import zsantana.economy.Bank;

/**
 * A test bank, represents currency in dollars
 * 
 * @author Zackary Santana
 *
 */
public class DollarBank extends Bank<Double> {

	public DollarBank() {
		super("Dollar", "Dollar Bank");
	}

	@Override
	protected boolean load() {
		this._CURRENCY.put("Test", 50.0);
		return true;
	}

	@Override
	protected boolean unload() {
		return true;
	}

	@Override
	@Deprecated
	public boolean amend(String key, Double value) {
		this._CURRENCY.put(key, this._CURRENCY.get(key) + value);
		return true;
	}
	
	public double add(String key, Double value) {
		double finalAmount = this._CURRENCY.get(key) + value;
		this._CURRENCY.put(key, finalAmount);
		return finalAmount;
	}
}
