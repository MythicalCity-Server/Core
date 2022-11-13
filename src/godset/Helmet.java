package godset;

import org.bukkit.Location;
import org.bukkit.Material;

import zsantana.customitems.data.Listening;
import zsantana.customitems.data.Rarity;
import zsantana.customitems.data.RarityArmor;
import zsantana.customitems.data.Slot;
import zsantana.customitems.events.DamageEntityEvent;
import zsantana.customitems.events.ToggleSneakEvent;

public class Helmet extends RarityArmor {

    public Helmet() {
        super(Material.IRON_HELMET, "Helmet of Gods",
                "These were once owned by the gods!\n&7Take with a grain of salt...", Rarity.TOP_SECRET);
    }

    @Listening(description = "Attacking an enemy sends them far away!")
    public void damageEntity(DamageEntityEvent event) {
        event.getPlayer().sendMessage("Mulitplied velocity");
        event.getEntity().setVelocity(event.getPlayer().getLocation().getDirection().multiply(5));
    }

    @Listening(description = "Sneak to summon a wall")
    public void sneak(ToggleSneakEvent event) {
        if (event.getPlayer().isSneaking())
            return;
        int bound = 2;
        Location base = event.getPlayer().getLocation().add(event.getPlayer().getLocation().getDirection().multiply(4));
        for (int y = 1; y < 30; ++y)
            for (int x = -bound; x <= bound; ++x) {
                for (int z = -bound; z <= bound; ++z) {
                    if (Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) < bound)
                        continue;
                    Location location = base.clone();
                    location.add(x, y, z);
                    location.getWorld().spawnFallingBlock(location, Material.FIRE.createBlockData());
                }
            }
    }

    @Override
    public Slot getSlot() {
        return Slot.HELMET;
    }
}
