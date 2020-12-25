package zsantana.customitems.data;

public enum Slot {

	HELMET, CHESTPLATE, LEGGINGS, BOOTS, HAND, ALL, NA;
	
	boolean isAllowed(Slot compare) {
		return this.equals(Slot.ALL) || compare.equals(Slot.ALL) || this.equals(compare);
	}
	
	static boolean isAllowed(Slot slot1, Slot slot2) {
		return slot1.equals(Slot.ALL) || slot2.equals(Slot.ALL) || slot1.equals(slot2);
	}
}
