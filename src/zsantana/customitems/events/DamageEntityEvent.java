package zsantana.customitems.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

public class DamageEntityEvent extends CancellableEvent {

	private final Entity _ENTITY;

	public DamageEntityEvent(Player player, ItemStack itemStack, Entity defender) {
		super(player, itemStack);

		this._ENTITY = defender;;
	}

	public Entity getEntity() {
		return this._ENTITY;
	}
}
