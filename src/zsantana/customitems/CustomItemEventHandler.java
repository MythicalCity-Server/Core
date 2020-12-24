package zsantana.customitems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import zsantana.customitems.events.DropEvent;
import zsantana.customitems.events.Event;
import zsantana.customitems.events.Event.CancellableEvent;
import zsantana.customitems.events.InteractEvent;
import zsantana.customitems.events.ItemBreakEvent;
import zsantana.customitems.events.ItemConsumeEvent;
import zsantana.customitems.events.PickUpEvent;
import zsantana.customitems.events.PickUpItemEvent;
import zsantana.customitems.events.SwitchItemEvent;
import zsantana.customitems.events.ToggleSneakEvent;
import zsantana.customitems.events.ToggleSprintEvent;

/**
 * Manages registering the events to a map and calling them when the event
 * triggers
 * 
 * @author Zackary Santana
 *
 */
public class CustomItemEventHandler implements Listener {

	private final Map<Class<? extends Event>, Map<Function<ItemStack, Boolean>, Consumer<Event>>> _EVENTS;

	/**
	 * Sets the hook in Custom Item class and starts the event map
	 */
	public CustomItemEventHandler() {
		CustomItem.setEventHandler(this);

		this._EVENTS = new HashMap<>();
	}

	/**
	 * Registers a new action to run when the event type and custom item are both
	 * happening in the same event
	 * 
	 * @param eventType  The event you would like to listen for
	 * @param customItem The custom item that is listening to the event
	 * @param run        The action to do when this event happens
	 */
	public void register(Class<? extends Event> eventType, CustomItem customItem, Consumer<Event> run) {
		if (!this._EVENTS.containsKey(eventType))
			this._EVENTS.put(eventType, new HashMap<>());
		this._EVENTS.get(eventType).put((item) -> {
			return customItem.isApplicable(item);
		}, (event) -> {
			run.accept(event);
		});
	}

	// Below is all the events that run and must loop through the stored actions

	// INTERACT EVENT

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		ItemStack itemStack = event.getItem();
		Action action = event.getAction();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(InteractEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(itemStack)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), itemStack, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				return;
			}
		}
	}

	// DROP EVENT

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
		ItemStack itemStack = event.getItemDrop().getItemStack();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(DropEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(itemStack)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), itemStack, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				return;
			}
		}
	}

	// ITEM BREAK EVENT

	@EventHandler
	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) {
		ItemStack itemStack = event.getBrokenItem();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(ItemBreakEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(itemStack)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), itemStack));
				return;
			}
		}
	}

	// ITEM CONSUME EVENT

	@EventHandler
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
		ItemStack itemStack = event.getItem();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(ItemBreakEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(itemStack)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), itemStack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
				return;
			}
		}
	}

	// ITEM HELD EVENT

	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
		int newSlot = event.getNewSlot(), oldSlot = event.getPreviousSlot();
		Inventory inven = event.getPlayer().getInventory();
		ItemStack current = inven.getItem(newSlot), old = inven.getItem(oldSlot);
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(SwitchItemEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(current)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				return;
			}
		}
	}

	// PLAYER PICK UP ITEM EVENT
	// PICKING UP AN ITEM EVENT

	@EventHandler
	public void onPickupItemEvent(PlayerPickupItemEvent event) {
		PlayerInventory inven = event.getPlayer().getInventory();
		ItemStack pickup = event.getItem().getItemStack();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(PickUpEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(pickup)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(event.getPlayer(), event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				return;
			}
		}
		ItemStack mainHand = inven.getItemInHand();
		events = _EVENTS.get(PickUpItemEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(event.getPlayer(), event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				return;
			}
		}
	}
	
	// TOGGLE SNEAK EVENT

	@EventHandler
	public void onToggleSneakEvent(PlayerToggleSneakEvent event) {
		PlayerInventory inven = event.getPlayer().getInventory();
		ItemStack itemstack = inven.getItemInHand();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(ToggleSneakEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(itemstack)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), itemstack));
				return;
			}
		}
	}

	@EventHandler
	public void onToggleSprintEvent(PlayerToggleSprintEvent event) {
		PlayerInventory inven = event.getPlayer().getInventory();
		ItemStack itemstack = inven.getItemInHand();
		Map<Function<ItemStack, Boolean>, Consumer<Event>> events = _EVENTS.get(ToggleSprintEvent.class);
		for (Function<ItemStack, Boolean> tests : events.keySet()) {
			if (tests.apply(itemstack)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), itemstack));
				return;
			}
		}
	}
}