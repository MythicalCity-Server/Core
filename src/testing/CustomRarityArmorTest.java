package testing;

import org.bukkit.Material;

import zsantana.customitems.data.Listening;
import zsantana.customitems.data.Rarity;
import zsantana.customitems.data.RarityArmor;
import zsantana.customitems.data.Slot;
import zsantana.customitems.events.DamageEntityEvent;

public class CustomRarityArmorTest extends RarityArmor {
	
	public CustomRarityArmorTest() {
		super(Material.DIAMOND_HELMET, "Protecter", "These were once owned by the gods!\n&7Take with a grain of salt...", Rarity.TOP_SECRET);
	}
	
	@Listening(description = "Attacking an enemy sends them far away!")
	public void damageEntity(DamageEntityEvent event) {
		event.getEntity().setVelocity(event.getPlayer().getLocation().getDirection().multiply(5));
	}
	
	@Override
	public Slot getSlot() {
		return Slot.HELMET;
	}
}
