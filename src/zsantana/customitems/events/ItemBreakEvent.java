package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemBreakEvent extends Event {

	public ItemBreakEvent(Player player, ItemStack itemStack) {
		super(player, itemStack);
	}
}
