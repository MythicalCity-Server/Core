package zsantana.customitems.data;

import org.bukkit.inventory.ItemStack;

public abstract class CustomArmor extends CustomItem {
	
	/**
	 * Tests if this custom item is indeed the itemstack provided
	 * 
	 * @param item An itemstack to check
	 * @param slot The slot of the armor
	 * @return If this custom item is the itemstack provided
	 */
	public final boolean isApplicable(ItemStack item, Slot slot) {
		return (slot.equals(Slot.ALL) || getSlot().equals(Slot.ALL) || slot.equals(getSlot())) && getItem().isSimilar(item);
	}
	
	public abstract Slot getSlot();
}
