package testing;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import zsantana.entitydrops.CustomEntity;

public class CustomEntityTest extends CustomEntity {

    public CustomEntityTest() {
        super(EntityType.SHEEP, "My son (drops boat)", new ItemStack(Material.ACACIA_BOAT), null, null, null, null,
                null);
        addDrop(EquipmentSlot.HEAD, 100);
    }
}
