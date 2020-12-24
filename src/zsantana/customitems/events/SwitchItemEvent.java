package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event.CancellableEvent;

public class SwitchItemEvent extends CancellableEvent {
	
	private final ItemStack _OLD;

	public SwitchItemEvent(Player player, ItemStack old, ItemStack currentItem) {
		super(player, currentItem);
		this._OLD = old;
	}
	
	@Override
	@Deprecated
	public ItemStack getItemStack() {
		return super.getItemStack();
	}
	
	public ItemStack getOld() {
		return this._OLD;
	}
	
	public ItemStack getCurrent() {
		return super.getItemStack();
	}
}
