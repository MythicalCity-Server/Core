package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The Interact class that handles right/left clicks
 * 
 * @author Zackary Santana
 *
 */
public class InteractEvent extends CancellableEvent {

	private final Action _ACTION;

	/**
	 * Creates a new Interact Event
	 * 
	 * @param player    The player who did the event
	 * @param itemStack The itemstack that is involved in the event
	 * @param action    The action the user did (i.e. left click air, right click a
	 *                  block, ect...)
	 */
	public InteractEvent(Player player, ItemStack itemStack, Action action) {
		super(player, itemStack);

		this._ACTION = action;
	}

	/**
	 * @return The action of the player
	 */
	public Action getAction() {
		return this._ACTION;
	}
}
