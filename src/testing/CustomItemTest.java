package testing;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.data.CustomItem;
import zsantana.customitems.data.Listening;
import zsantana.customitems.events.DamageEntityEvent;
import zsantana.customitems.events.InteractEvent;
import zsantana.customitems.events.ToggleSprintEvent;
import zsantana.misc.ItemFactory;

public class CustomItemTest extends CustomItem {
	
	private ItemStack _item;
	
	public CustomItemTest() {
		this._item = ItemFactory.createItem(Material.STICK, 1, "Da stick", "wack wack", "", "This is a test item");
	}
	
	@Listening
	public void test(InteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			Location location = event.getPlayer().getLocation().add(event.getPlayer().getLocation().getDirection().multiply(5));
			while (location.getBlock().getType() != Material.AIR) {
				location = location.add(0, 1, 0);
			}
			event.getPlayer().teleport(location);
		}
	}
	
	@Listening
	public void sprinting(ToggleSprintEvent event) {
		event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().normalize());
	}
	
	@Listening
	public void entityDamage(DamageEntityEvent event) {
		event.getPlayer().sendMessage("STOP");
	}

	@Override
	public ItemStack getItem() {
		return this._item;
	}
}
