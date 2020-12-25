package testing;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import zsantana.customitems.data.CustomArmor;
import zsantana.customitems.data.Listening;
import zsantana.customitems.data.Slot;
import zsantana.customitems.events.InteractEvent;
import zsantana.customitems.events.ToggleSprintEvent;

public class CustomArmorTest extends CustomArmor {

	@Listening
	public void sprint(ToggleSprintEvent event) {
		event.getPlayer().sendMessage("Hey how are you");
	}
	
	@Listening
	public void interact(InteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			PlayerInventory inventory = event.getPlayer().getInventory();
			event.getPlayer().sendMessage("HI");
			if (inventory.getHelmet() == null || inventory.getHelmet().getType() == Material.AIR) {
				inventory.setHelmet(getItem());
				event.getItemStack().setAmount(0);
			}
		}
	}
	
	@Override
	public Slot getSlot() {
		return Slot.ALL;
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(Material.YELLOW_FLOWER);
	}

}
