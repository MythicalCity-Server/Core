package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Toggle Sprint class that handles when a player toggles their sprint
 * 
 * @author Zackary Santana
 *
 */
public class ToggleSprintEvent extends Event {

	/**
	 * Creates a new Toggle Sprint Event
	 * 
	 * @param player    The player who toggled their sprint
	 * @param itemstack The itemstack that is involved in the event
	 */
	public ToggleSprintEvent(Player player, ItemStack itemstack) {
		super(player, itemstack);
	}
}
