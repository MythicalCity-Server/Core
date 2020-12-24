package zsantana.customitems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event;
import zsantana.customitems.events.InteractEvent;

/**
 * Manages registering the events to a map and calling them when the event triggers
 * 
 * @author Zackary Santana
 *
 */
public class EventHandler implements Listener {

	private final Map<Class<? extends Event>, Map<Function<ItemStack, Boolean>, Consumer<Event>>> _EVENTS;

	/**
	 * Sets the hook in Custom Item class and starts the event map
	 */
	public EventHandler() {
		CustomItem.setEventHandler(this);

		this._EVENTS = new HashMap<>();
	}

	/**
	 * Registers a new action to run when the event type and custom item are both happening in the same event
	 * 
	 * @param eventType The event you would like to listen for
	 * @param customItem The custom item that is listening to the event
	 * @param run The action to do when this event happens
	 */
	public void register(Class<? extends Event> eventType, CustomItem customItem, Consumer<Event> run) {
		if (!this._EVENTS.containsKey(eventType)) this._EVENTS.put(eventType, new HashMap<>());
		this._EVENTS.get(eventType).put((item) ->{
			return customItem.isApplicable(item);
		}, (event) -> {
			run.accept(event);
		});
	}
	
	// Below is all the events that run and must loop through the stored actions

	@org.bukkit.event.EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		ItemStack itemStack = event.getItem();
		Action action = event.getAction();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> _interact_event = _EVENTS.get(InteractEvent.class);
		for (Function<ItemStack, Boolean> test : _interact_event.keySet()) {
			if (test.apply(itemStack)) {
				Consumer<Event> run = _interact_event.get(test);
				InteractEvent temp = new InteractEvent(event.getPlayer(), itemStack, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}
		}
	}
}