package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

public class ItemConsumeEvent extends CancellableEvent {

	private ItemStack _itemStack;

	public ItemConsumeEvent(Player player, ItemStack itemStack) {
		super(player, itemStack);
		this._itemStack = itemStack;
	}

	@Override
	public ItemStack getItemStack() {
		return this._itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this._itemStack = itemStack;
	}
}
