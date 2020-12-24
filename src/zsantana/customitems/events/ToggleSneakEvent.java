package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToggleSneakEvent extends Event {
	
	public ToggleSneakEvent(Player player, ItemStack itemstack) {
		super(player, itemstack);
	}
}
