package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemBreakEvent extends Event {

	/**
	 * Creates a new Interact Event
	 * 
	 * @param player    The player who did the event
	 * @param itemStack The itemstack that is involved in the event
	 */
	public ItemBreakEvent(Player player, ItemStack itemStack) {
		super(player, itemStack);
	}
}
