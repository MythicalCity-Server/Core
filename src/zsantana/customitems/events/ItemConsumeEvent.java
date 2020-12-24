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

	/**
	 * @return The itemstack that is involved in the event
	 */
	@Override
	public ItemStack getItemStack() {
		return this._itemStack;
	}

	/**
	 * Sets the itemstack
	 * 
	 * @param itemStack The new instance of itemstack
	 */
	public void setItemStack(ItemStack itemStack) {
		this._itemStack = itemStack;
	}
}
