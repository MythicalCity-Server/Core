package zsantana.customitems.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

public class DropEvent extends CancellableEvent {

	private final Item _ITEM;

	/**
	 * Creates a new Interact Event
	 * 
	 * @param player     The player who did the event
	 * @param itemStack  The itemstack that is involved in the event
	 * @param itemEntity The entity version of the item dropped
	 */
	public DropEvent(Player player, ItemStack itemStack, Item itemEntity) {
		super(player, itemStack);

		this._ITEM = itemEntity;
	}

	/**
	 * @return The action of the player
	 */
	public Item getItem() {
		return this._ITEM;
	}
}
