package zsantana.entitydrops;

import java.util.function.BiConsumer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import zsantana.misc.ItemFactory;

public class CustomEntity {

    private final static EquipmentSlot[] _SLOTS;

    static {
        _SLOTS = new EquipmentSlot[6];
        _SLOTS[0] = EquipmentSlot.HEAD;
        _SLOTS[1] = EquipmentSlot.CHEST;
        _SLOTS[2] = EquipmentSlot.LEGS;
        _SLOTS[3] = EquipmentSlot.FEET;
        _SLOTS[4] = EquipmentSlot.HAND;
        _SLOTS[5] = EquipmentSlot.OFF_HAND;
    }

    private final String _ID, _DISPLAY_NAME;
    private final EntityType _TYPE;
    private final ItemStack[] _GEAR;

    private boolean _checkByDisplayNameOnly;
    private double _spawnRate;

    public CustomEntity(EntityType type, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots,
            ItemStack mainHand, ItemStack offHand) {
        this._DISPLAY_NAME = "";
        this._TYPE = type;

        this._GEAR = new ItemStack[6];
        this._GEAR[0] = helmet;
        this._GEAR[1] = chestplate;
        this._GEAR[2] = leggings;
        this._GEAR[3] = boots;
        this._GEAR[4] = mainHand;
        this._GEAR[5] = offHand;

        StringBuilder id = new StringBuilder(type.name());

        id.append(createID());

        this._ID = id.toString();
    }

    public CustomEntity(EntityType type, String displayName, ItemStack helmet, ItemStack chestplate, ItemStack leggings,
            ItemStack boots, ItemStack mainHand, ItemStack offHand) {
        this._DISPLAY_NAME = ChatColor.translateAlternateColorCodes('&', displayName);
        this._TYPE = type;

        this._GEAR = new ItemStack[6];
        this._GEAR[0] = helmet;
        this._GEAR[1] = chestplate;
        this._GEAR[2] = leggings;
        this._GEAR[3] = boots;
        this._GEAR[4] = mainHand;
        this._GEAR[5] = offHand;

        StringBuilder id = new StringBuilder(type.name() + ":" + ChatColor.stripColor(this._DISPLAY_NAME));

        id.append(createID());

        this._ID = id.toString();
    }

    public final Entity spawnEntity(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, this._TYPE);
        entity.setCustomName(this._DISPLAY_NAME);
        entity.setCustomNameVisible(true);

        if (entity instanceof LivingEntity) {
            LivingEntity lEntity = (LivingEntity) entity;
            for (int i = 0; i < 6; ++i) {
                if (this._GEAR[i] != null && this._GEAR[i].getType() != Material.AIR) {
                    // lEntity.getEquipment().setItem(_SLOTS[i], this._GEAR[i]);
                    ItemFactory.setEquipmentSlot(lEntity, _SLOTS[i], this._GEAR[i]);
                }
            }
        }

        return entity;
    }

    public final void addDrop(ItemStack item, int chance) {
        EntityDropsHandler.addEvent(this._TYPE, (entity, player) -> {
            if (applicable(entity)) {
                entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), item);
            }
        }, chance);
    }

    public final void addDrop(EquipmentSlot slot, int chance) {
        EntityDropsHandler.addEvent(this._TYPE, (entity, player) -> {
            if (applicable(entity)) {
                int i;
                for (i = 0; i < 6; ++i) {
                    if (_SLOTS[i] == slot) {
                        break;
                    }
                }
                entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), this._GEAR[i]);
            }
        }, chance);
    }

    public final void addDrop(EquipmentSlot slot, int remainingDurability, int chance) {
        EntityDropsHandler.addEvent(this._TYPE, (entity, player) -> {
            if (applicable(entity)) {
                int i;
                for (i = 0; i < 6; ++i) {
                    if (_SLOTS[i] == slot) {
                        break;
                    }
                }
                ItemStack item = this._GEAR[i].clone();
                ItemMeta meta = item.getItemMeta();
                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(item.getType().getMaxDurability() - remainingDurability);
                    item.setItemMeta(meta);
                }
                entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), item);
            }
        }, chance);
    }

    public final void addEvent(BiConsumer<LivingEntity, Player> toRun, int chance) {
        EntityDropsHandler.addEvent(this._TYPE, (entity, player) -> {
            if (applicable(entity)) {
                toRun.accept(entity, player);
            }
        }, chance);
    }

    public final void setNaturallySpawningRate(double spawnRate) {
        if (this._spawnRate == 0) {
            EntityDropsHandler.addNaturallySpawning(this);
        }
        this._spawnRate = spawnRate;
    }

    public final void setNaturallySpawningRate(double spawnRate, EntityType toReplace) {
        if (this._spawnRate == 0) {
            EntityDropsHandler.addNaturallySpawning(this, toReplace);
        }
        this._spawnRate = spawnRate;
    }

    public final void checkByDisplayNameOnly(boolean checkByDisplayNameOnly) {
        this._checkByDisplayNameOnly = checkByDisplayNameOnly;
    }

    public final double getNaturalSpawnRate() {
        return this._spawnRate;
    }

    public final EntityType getType() {
        return this._TYPE;
    }

    private boolean applicable(Entity entity) {
        if (this._checkByDisplayNameOnly) {
            return ChatColor.stripColor(entity.getCustomName()).equals(ChatColor.stripColor(this._DISPLAY_NAME));
        }

        StringBuilder testID = new StringBuilder(entity.getType().name());

        if (!this._DISPLAY_NAME.equals("")) {
            testID.append(":" + ChatColor.stripColor(entity.getCustomName()));
        }

        if (entity instanceof LivingEntity) {
            LivingEntity lEntity = (LivingEntity) entity;
            for (ItemStack item : lEntity.getEquipment().getArmorContents()) {
                if (item != null) {
                    if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                        testID.append(":" + item.getItemMeta().getDisplayName());
                    } else if (item.getType() != Material.AIR) {
                        testID.append(":" + item.getType().name());
                    }
                }
            }
        }

        boolean contains = true;
        for (String section : testID.toString().split(":")) {
            if (!this._ID.contains(section)) {
                contains = false;
                break;
            }
        }

        return contains;
    }

    private String createID() {
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 6; ++i) {
            if (this._GEAR[i] != null) {
                if (this._GEAR[i].hasItemMeta() && this._GEAR[i].getItemMeta().hasDisplayName()) {
                    id.append(":" + this._GEAR[i].getItemMeta().getDisplayName());
                } else if (this._GEAR[i].getType() != Material.AIR) {
                    id.append(":" + this._GEAR[i].getType().name());
                }
            }

        }
        return id.toString();
    }
}
