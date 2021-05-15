package zsantana.customitems.data;

public enum Rarity {
	
	PLAIN("Plain", "&f"), UNCOMMON("Uncommon", "&a"), SECRET("Secret", "&b"), TOP_SECRET("Top Secret", "&4"), LEGENDARY("Legendary", "&6"), UNCLASSIFIED("Unclassified", "&5");
	
	private final String _NAME, _COLOR;
	
	Rarity(String name, String color) {
		this._NAME = name;
		this._COLOR = color;
	}
	
	public String getName() {
		return this._NAME;
	}
	
	public String getColor() {
		return this._COLOR;
	}
}
