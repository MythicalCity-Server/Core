package zsantana.customitems.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The Pick Up Item class that handles when a player picks up ANY itemstack
 * 
 * @author Zackary Santana
 *
 */
public class PickUpItemEvent extends CancellableEvent {

	private final Item _ITEM;

	/**
	 * Creates a new Pick Up Item Event
	 * 
	 * @param player The player who picked up an itemstack
	 * @param item   The (ANY) item that was picked up
	 */
	public PickUpItemEvent(Player player, Item item) {
		super(player, item.getItemStack());

		this._ITEM = item;
	}

	/**
	 * @return The (ANY) item that was picked up
	 */
	public Item getItem() {
		return this._ITEM;
	}
}
