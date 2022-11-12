package testing;

import org.bukkit.Material;

import zsantana.customitems.data.Listening;
import zsantana.customitems.data.Rarity;
import zsantana.customitems.data.RarityItem;
import zsantana.customitems.data.Slot;
import zsantana.customitems.events.DamageEntityEvent;

public class CustomRarityItemTest extends RarityItem {

    public CustomRarityItemTest() {
        super(Material.STICK, "Waker", "These were once owned by the gods!\n&7Take with a grain of salt...",
                Rarity.UNCLASSIFIED);
    }

    @Listening(description = "Attacking an enemy sends them far away!")
    public void damageEntity(DamageEntityEvent event) {
        event.getPlayer().sendMessage("Mulitplied velocity 2");
        event.getEntity().setVelocity(event.getPlayer().getLocation().getDirection().multiply(5));
    }

    @Override
    public Slot getSlot() {
        return Slot.MAIN_HAND;
    }
}
