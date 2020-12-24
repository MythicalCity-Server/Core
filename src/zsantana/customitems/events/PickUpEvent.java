package zsantana.customitems.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import zsantana.customitems.events.Event.CancellableEvent;

public class PickUpEvent extends CancellableEvent {
	
	private final Item _ITEM;
	
	public PickUpEvent(Player player, Item item) {
		super(player, item.getItemStack());
		
		this._ITEM = item;
	}
	
	public Item getItem() {
		return this._ITEM;
	}
}
