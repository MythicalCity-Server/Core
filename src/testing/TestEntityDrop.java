package testing;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import zsantana.entitydrops.EntityDropsHandler;

public class TestEntityDrop {

    public TestEntityDrop() {
        EntityDropsHandler.addDrop(EntityType.ZOMBIE, new ItemStack(Material.DIAMOND_AXE), 100);
    }
}
