package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemBreakEvent extends Event {
	
	private final ItemStack _BREAKING;

	public ItemBreakEvent(Player player, ItemStack itemStack, ItemStack breaking) {
		super(player, itemStack);
		
		this._BREAKING = breaking;
	}
	
	public ItemStack getBrokenItem() {
		return this._BREAKING;
	}
}
