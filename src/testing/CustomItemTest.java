package testing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.data.CustomItem;
import zsantana.customitems.data.Listening;
import zsantana.customitems.events.InteractEvent;
import zsantana.misc.ItemFactory;

public class CustomItemTest extends CustomItem {
	
	private ItemStack _item;
	
	public CustomItemTest() {
		this._item = ItemFactory.createItem(Material.STICK, 1, "Da stick", "wack wack", "", "This is a test item");
	}
	
	@Listening
	public void test(InteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR) {
			Location location = event.getPlayer().getLocation().add(event.getPlayer().getLocation().getDirection().multiply(5));
			while (location.getBlock().getType() != Material.AIR && location.getBlock().getType() != Material.WATER && location.getBlock().getType() != Material.STATIONARY_WATER) {
				location = location.add(0, 1, 0);
			}
			event.getPlayer().teleport(location);
		}
	}

	@Override
	public ItemStack getItem() {
		return this._item;
	}
}
