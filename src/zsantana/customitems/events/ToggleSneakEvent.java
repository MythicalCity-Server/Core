package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The Toggle Sneak class that handles when a player toggles their sneak
 * 
 * @author Zackary Santana
 *
 */
public class ToggleSneakEvent extends Event {

	/**
	 * Creates a new Toggle Sneak Event
	 * 
	 * @param player    The player who toggled their sneak
	 * @param itemstack The itemstack that is involved in the event
	 */
	public ToggleSneakEvent(Player player, ItemStack itemstack) {
		super(player, itemstack);
	}
}
