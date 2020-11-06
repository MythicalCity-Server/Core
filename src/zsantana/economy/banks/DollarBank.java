package zsantana.economy.banks;

import zsantana.economy.Bank;

public class DollarBank extends Bank {

	public DollarBank() {
		super("Dollar");
	}

	@Override
	protected boolean loadBank() {
		this._CURRENCY.put("Test", 50.0);
		
		return true;
	}
}
