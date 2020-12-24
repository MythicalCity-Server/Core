package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToggleSprintEvent extends Event {
	
	public ToggleSprintEvent(Player player, ItemStack itemstack) {
		super(player, itemstack);
	}
}
