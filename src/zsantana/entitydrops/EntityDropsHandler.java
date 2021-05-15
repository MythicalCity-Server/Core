package zsantana.entitydrops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

import zsantana.handlers.Handler;

/**
 * A skeleton handler class that just manages registering the event listener
 * 
 * @author Zackary Santana
 *
 */
public class EntityDropsHandler extends Handler implements Listener {

	private static EntityDropsHandler _HANDLER;

	public static void addDrop(EntityType type, ItemStack drop, double chance) {
		_HANDLER.registerDrop(type, drop, chance);
	}

	public static void addDrop(EntityType type, ItemStack drop, double chance,
			Function<LivingEntity, Boolean> testKilled) {
		_HANDLER.registerDrop(type, drop, chance, testKilled);
	}

	public static void addDrop(EntityType type, ItemStack drop, double chance,
			Function<LivingEntity, Boolean> testKilled, Function<Player, Boolean> testKiller) {
		_HANDLER.registerDrop(type, drop, chance, testKilled, testKiller);
	}

	public static void addEvent(EntityType type, BiConsumer<LivingEntity, Player> toRun, double chance) {
		_HANDLER.registerEvent(type, toRun, chance);
	}

	public static void addNaturallySpawning(CustomEntity entity) {
		_HANDLER._CUSTOM_ENTITIES.get(entity.getType()).add(entity);
	}

	public static void addNaturallySpawning(CustomEntity entity, EntityType toReplace) {
		_HANDLER._CUSTOM_ENTITIES.get(toReplace).add(entity);
	}

	private Map<EntityType, List<BiConsumer<LivingEntity, Player>>> _DROPS;
	private Map<EntityType, List<CustomEntity>> _CUSTOM_ENTITIES;

	@Override
	protected void enable() {
		this._DROPS = new HashMap<>();
		this._CUSTOM_ENTITIES = new HashMap<>();
		_HANDLER = this;
		Bukkit.getPluginManager().registerEvents(this, _CORE);
		for (EntityType type : EntityType.values()) {
			this._DROPS.put(type, new ArrayList<>());
			this._CUSTOM_ENTITIES.put(type, new ArrayList<>());
		}
	}

	public void registerDrop(EntityType type, ItemStack drop, double chance) {
		this._DROPS.get(type).add((entity, player) -> {
			double random = Math.random();
			if (chance / 100 >= random) {
				entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), drop);
			}
		});
	}

	public void registerDrop(EntityType type, ItemStack drop, double chance,
			Function<LivingEntity, Boolean> testKilled) {
		this._DROPS.get(type).add((entity, player) -> {
			double random = Math.random();
			if (chance / 100 >= random && testKilled.apply((LivingEntity) entity)) {
				entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), drop);
			}
		});
	}

	public void registerDrop(EntityType type, ItemStack drop, double chance, Function<LivingEntity, Boolean> testKilled,
			Function<Player, Boolean> testKiller) {
		if (testKilled == null) {
			this._DROPS.get(type).add((entity, player) -> {
				double random = Math.random();
				if (chance / 100 >= random && testKiller.apply(player)) {
					entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), drop);
				}
			});
		} else {
			this._DROPS.get(type).add((entity, player) -> {
				double random = Math.random();
				if (chance / 100 >= random && testKilled.apply((LivingEntity) entity) && testKiller.apply(player)) {
					entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), drop);
				}
			});
		}
	}

	public void registerEvent(EntityType type, BiConsumer<LivingEntity, Player> toRun, double chance) {
		this._DROPS.get(type).add((entity, player) -> {
			double random = Math.random();
			if (chance / 100 >= random) {
				toRun.accept((LivingEntity) entity, player);
			}
		});
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity)) {
			return;
		}
		LivingEntity lEntity = (LivingEntity) entity;
		if (event.getEntity().getKiller() instanceof Player) {
			Player player = (Player) event.getEntity().getKiller();
			for (BiConsumer<LivingEntity, Player> drops : this._DROPS.get(entity.getType())) {
				drops.accept(lEntity, player);
			}
		} else {
			for (BiConsumer<LivingEntity, Player> drops : this._DROPS.get(entity.getType())) {
				drops.accept(lEntity, null);
			}
		}
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		Bukkit.getScheduler().runTask(_CORE, () -> {
			if (!event.getEntity().isCustomNameVisible()) {
				double rate = 0;
				double chance = Math.random() * 100;
				for (CustomEntity entity : this._CUSTOM_ENTITIES.get(event.getEntity().getType())) {
					rate += entity.getNaturalSpawnRate();
					if (rate > chance) {
						entity.spawnEntity(event.getLocation());
						event.getEntity().remove();
						break;
					}
				}
			}
		});
	}

	@Override
	protected void disable() {
		// TODO Auto-generated method stub
		_HANDLER = null;
	}
}
