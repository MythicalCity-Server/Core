package testing;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.CustomItem;
import zsantana.customitems.data.Listening;
import zsantana.customitems.events.InteractEvent;

public class CustomItemTest extends CustomItem {
	
	public CustomItemTest() {
		registerAction(InteractEvent.class, (event) -> {
			InteractEvent i = (InteractEvent) event;
			event.getPlayer().sendMessage("TEST");
			if (i.getAction() == Action.LEFT_CLICK_AIR) {
				event.getPlayer().sendMessage("WOW");
			}
		});
	}
	
	@Listening
	public void test(InteractEvent event) {
		System.out.println("VERY COOL INDEEEED");
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Material.APPLE);
	}
}
