package zsantana.customitems.data;

/**
 * An enumeration that stores the different slots for items and comparing slots
 * methods
 * 
 * @author Zackary Santana
 *
 */
public enum Slot {

	HELMET, CHESTPLATE, LEGGINGS, BOOTS, MAIN_HAND, OFF_HAND, FLOOR, ALL, NA;

	/**
	 * Checks if the slot to compare is compatible with this slot
	 * 
	 * @param compare The slot to compare with
	 * @return If this slot and the compare slot are compatible
	 */
	boolean isAllowed(Slot compare) {
		return this.equals(Slot.ALL) || compare.equals(Slot.ALL) || this.equals(compare);
	}

	/**
	 * Checks if slot1 and slot2 are compatible with this slot
	 * 
	 * @param slot1 The 1st slot to compare with
	 * @param slot2 The 2nd slot to compare with
	 * @return If slot1 and slot2 are compatible
	 */
	static boolean isAllowed(Slot slot1, Slot slot2) {
		return slot1.equals(Slot.ALL) || slot2.equals(Slot.ALL) || slot1.equals(slot2);
	}
}
