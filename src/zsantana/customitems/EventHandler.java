package zsantana.customitems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event;
import zsantana.customitems.events.LeftClickAirEvent;

public class EventHandler implements Listener {

	private final Map<Class<? extends Event>, Map<Function<ItemStack, Boolean>, Consumer<LeftClickAirEvent>>> _EVENTS;

	public EventHandler(CustomItemsHandler handler) {
		this._EVENTS = new HashMap<>();
		this._EVENTS.put(LeftClickAirEvent.class, new HashMap<>());
	}

	public void register(CustomItem customItem) {
		for (Class<? extends Event> listening : customItem.register()) {
			this._EVENTS.get(listening).put((item) -> {
				return customItem.isApplicable(item);
			}, (event) -> {
				customItem.runEvent(event, listening);
			});
		}
	}

}