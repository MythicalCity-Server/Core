package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

public class ItemConsumeEvent extends CancellableEvent {

	private ItemStack _consumed;

	public ItemConsumeEvent(Player player, ItemStack itemStack, ItemStack consumed) {
		super(player, itemStack);
		this._consumed = consumed;
	}

	public ItemStack getConsumed() {
		return this._consumed;
	}

	public void setConsumed(ItemStack itemStack) {
		this._consumed = itemStack;
	}
}
