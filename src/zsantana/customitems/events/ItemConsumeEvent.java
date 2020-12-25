package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The Item Consume class that handles when a player consumes an item
 * 
 * @author Zackary Santana
 *
 */
public class ItemConsumeEvent extends CancellableEvent {

	private ItemStack _consumed;

	/**
	 * Creates a new Item Consume Event
	 * 
	 * @param player    The player who consumed an item
	 * @param itemStack The itemstack that is involved in the event
	 * @param consumed  The itemstack that is consumed
	 */
	public ItemConsumeEvent(Player player, ItemStack itemStack, ItemStack consumed) {
		super(player, itemStack);

		this._consumed = consumed;
	}

	/**
	 * @return The consumed itemstack
	 */
	public ItemStack getConsumed() {
		return this._consumed;
	}

	/**
	 * Sets the consumed itemstack to another
	 * 
	 * @param itemStack A new itemstack to replace the consume one with
	 */
	public void setConsumed(ItemStack itemStack) {
		this._consumed = itemStack;
	}
}
