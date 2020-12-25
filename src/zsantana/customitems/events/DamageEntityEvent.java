package zsantana.customitems.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

/**
 * The DamageEntity class that handles when a player damages an entity
 * 
 * @author Zackary Santana
 *
 */
public class DamageEntityEvent extends CancellableEvent {

	private final Entity _ENTITY;

	/**
	 * Creates a new Damage Entity Event
	 * 
	 * @param player    The player who did damage
	 * @param itemStack The itemstack that is involved in the event
	 * @param defender  The entity who took damage
	 */
	public DamageEntityEvent(Player player, ItemStack itemStack, Entity defender) {
		super(player, itemStack);

		this._ENTITY = defender;
	}

	/**
	 * @return The entity that got damaged
	 */
	public Entity getEntity() {
		return this._ENTITY;
	}
}
