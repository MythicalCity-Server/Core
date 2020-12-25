package zsantana.customitems.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The Pick Up class that handles when a player picks up an itemstack
 * 
 * @author Zackary Santana
 *
 */
public class PickUpEvent extends CancellableEvent {

	private final Item _ITEM;

	/**
	 * Creates a new Pick Up Event
	 * 
	 * @param player The player who picked up the item
	 * @param item   The item that is being picked up
	 */
	public PickUpEvent(Player player, Item item) {
		super(player, item.getItemStack());

		this._ITEM = item;
	}

	/**
	 * @return The Item that is being picked up
	 */
	public Item getItem() {
		return this._ITEM;
	}
}
