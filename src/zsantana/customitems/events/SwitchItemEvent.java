package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The Switch Item class that handles when a player switches their item in hand
 * 
 * @author Zackary Santana
 *
 */
public class SwitchItemEvent extends CancellableEvent {

	private final ItemStack _OLD;

	/**
	 * Creates a new Switch Item Event
	 * 
	 * @param player      The player who switched items in their hand
	 * @param old         The old item that was in their hand
	 * @param currentItem The new/current item that was in their hand
	 */
	public SwitchItemEvent(Player player, ItemStack old, ItemStack currentItem) {
		super(player, currentItem);

		this._OLD = old;
	}

	@Override
	@Deprecated
	public ItemStack getItemStack() {
		return super.getItemStack();
	}

	/**
	 * @return The old item in their hand
	 */
	public ItemStack getOld() {
		return this._OLD;
	}

	/**
	 * @return The current/new item in their hand
	 */
	public ItemStack getCurrent() {
		return super.getItemStack();
	}
}
