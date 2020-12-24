package testing;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.CustomItem;
import zsantana.customitems.data.Listening;
import zsantana.customitems.events.DamageEntityEvent;
import zsantana.customitems.events.InteractEvent;
import zsantana.customitems.events.PickUpEvent;
import zsantana.customitems.events.ToggleSprintEvent;

public class CustomItemTest extends CustomItem {
	
	public CustomItemTest() {
		
	}
	
	@Listening
	public void test(InteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			event.getPlayer().teleport(event.getPlayer().getLocation().add(event.getPlayer().getLocation().getDirection().multiply(5)));
		}
	}
	
	@Listening
	public void cameron(PickUpEvent event) {
		event.getPlayer().sendMessage("You picked up an apple");
	}
	
	@Listening
	public void sprinting(ToggleSprintEvent event) {
		event.getPlayer().sendMessage("You are sprinting with an apple");
	}
	
	@Listening
	public void entityDamage(DamageEntityEvent event) {
		event.getPlayer().sendMessage("STOP");
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Material.APPLE);
	}
}
