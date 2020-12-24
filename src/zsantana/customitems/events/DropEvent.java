package zsantana.customitems.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

public class DropEvent extends CancellableEvent {

	private final Item _ITEM;

	public DropEvent(Player player, ItemStack itemStack, Item itemEntity) {
		super(player, itemStack);

		this._ITEM = itemEntity;
	}

	public Item getItem() {
		return this._ITEM;
	}
}
