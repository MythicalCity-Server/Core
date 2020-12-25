package zsantana.customitems.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The Drop class that handles when a player drops an item
 * 
 * @author Zackary Santana
 *
 */
public class DropEvent extends CancellableEvent {

	private final Item _ITEM;

	/**
	 * Creates a new Drop Event
	 * 
	 * @param player     The player who dropped an item
	 * @param itemStack  The itemstack that is involved in the event
	 * @param itemEntity The Item that is being dropped
	 */
	public DropEvent(Player player, ItemStack itemStack, Item itemEntity) {
		super(player, itemStack);

		this._ITEM = itemEntity;
	}

	/**
	 * @return The Item that was dropped
	 */
	public Item getItem() {
		return this._ITEM;
	}
}
