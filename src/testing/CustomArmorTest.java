package testing;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import zsantana.customitems.data.CustomItem;
import zsantana.customitems.data.Listening;
import zsantana.customitems.data.Slot;
import zsantana.customitems.events.InteractEvent;
import zsantana.customitems.events.ToggleSneakEvent;
import zsantana.misc.ItemFactory;

public class CustomArmorTest extends CustomItem {
	
	private ItemStack _item;
	
	public CustomArmorTest() {
		this._item = ItemFactory.createItem(Material.LEATHER_HELMET, 1, "A basic helmet");
	}
	
	@Listening(slot = Slot.MAIN_HAND, description = "Removes blindness")
	public void sneaking(ToggleSneakEvent event) {
		event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}

	@Listening
	public void interact(InteractEvent event) {
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2000, 10));
	}
	
	@Override
	public Slot getSlot() {
		return Slot.HELMET;
	}

	@Override
	public ItemStack getItem() {
		return this._item;
	}
}
