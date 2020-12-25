package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Item Break class that handles when a player's item breaks
 * 
 * @author Zackary Santana
 *
 */
public class ItemBreakEvent extends Event {

	private final ItemStack _BREAKING;

	/**
	 * Creates a new Item Break Event
	 * 
	 * @param player    The player who's item broke
	 * @param itemStack The itemstack that is involved in the event
	 * @param breaking  The itemstack that is breaking
	 */
	public ItemBreakEvent(Player player, ItemStack itemStack, ItemStack breaking) {
		super(player, itemStack);

		this._BREAKING = breaking;
	}

	/**
	 * @return The item that is now broken
	 */
	public ItemStack getBrokenItem() {
		return this._BREAKING;
	}
}
