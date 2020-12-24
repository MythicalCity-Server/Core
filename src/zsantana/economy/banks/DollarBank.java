package zsantana.economy.banks;

import zsantana.economy.Bank;

public class DollarBank extends Bank<Double> {

	public DollarBank() {
		super("Dollar");
	}

	@Override
	protected boolean loadBank() {
		this._CURRENCY.put("Test", 50.0);
		
		return true;
	}

	@Override
	protected boolean unloadBank() {
		return true;
	}

	@Override
	@Deprecated
	public boolean ammend(String key, Double value) {
		this._CURRENCY.put(key, this._CURRENCY.get(key) + value);
		return true;
	}
	
	public double add(String key, Double value) {
		double finalAmount = this._CURRENCY.get(key) + value;
		this._CURRENCY.put(key, finalAmount);
		return finalAmount;
	}
}
