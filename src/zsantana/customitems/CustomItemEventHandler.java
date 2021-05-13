package zsantana.customitems;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import zsantana.customitems.data.CustomArmor;
import zsantana.customitems.data.CustomItem;
import zsantana.customitems.data.Slot;
import zsantana.customitems.events.DamageEntityEvent;
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

	private final Map<Class<? extends Event>, Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>>> _EVENTS;

	/**
	 * Sets the hook in Custom Item class and starts the event map
	 */
	public CustomItemEventHandler() {
		CustomItem.setEventHandler(this);
		CustomArmor.setEventHandler(this);

		this._EVENTS = new HashMap<>();
		this._EVENTS.put(InteractEvent.class, new HashMap<>());
		this._EVENTS.put(DropEvent.class, new HashMap<>());
		this._EVENTS.put(ItemBreakEvent.class, new HashMap<>());
		this._EVENTS.put(ItemConsumeEvent.class, new HashMap<>());
		this._EVENTS.put(SwitchItemEvent.class, new HashMap<>());
		this._EVENTS.put(PickUpEvent.class, new HashMap<>());
		this._EVENTS.put(PickUpItemEvent.class, new HashMap<>());
		this._EVENTS.put(ToggleSneakEvent.class, new HashMap<>());
		this._EVENTS.put(ToggleSprintEvent.class, new HashMap<>());
		this._EVENTS.put(DamageEntityEvent.class, new HashMap<>());
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
		this._EVENTS.get(eventType).put((itemstack, slot) -> {
			return customItem.isApplicable(itemstack, slot);
		}, (event) -> {
			run.accept(event);
		});
	}

	/**
	 * Registers a new action to run when the event type and custom item are both
	 * happening in the same event
	 * 
	 * @param eventType  The event you would like to listen for
	 * @param customItem The custom item that is listening to the event
	 * @param run        The action to do when this event happens
	 */
	public void register(Class<? extends Event> eventType, CustomArmor customArmor, Consumer<Event> run) {
		if (!this._EVENTS.containsKey(eventType))
			this._EVENTS.put(eventType, new HashMap<>());
		this._EVENTS.get(eventType).put((itemstack, slot) -> {
			return customArmor.isApplicable(itemstack, slot);
		}, (event) -> {
			run.accept(event);
		});
	}

	/**
	 * Registers a new action to run when the event type and custom item are both
	 * happening in the same event
	 * 
	 * @param eventType  The event you would like to listen for
	 * @param customItem The custom item that is listening to the event
	 * @param customSlot A custom slot for the item to trigger in
	 * @param run        The action to do when this event happens
	 */
	public void register(Class<? extends Event> eventType, CustomArmor customArmor, Slot customSlot,
			Consumer<Event> run) {
		if (!this._EVENTS.containsKey(eventType))
			this._EVENTS.put(eventType, new HashMap<>());
		this._EVENTS.get(eventType).put((itemstack, slot) -> {
			return customArmor.isApplicable(itemstack, customSlot, slot);
		}, (event) -> {
			run.accept(event);
		});
	}

	// Below is all the events that run and must loop through the stored actions

	// INTERACT EVENT

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event) {
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Action action = event.getAction();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(InteractEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), mainHand, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), offHand, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), helmet, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), chestplate, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), leggings, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new InteractEvent(event.getPlayer(), boots, action);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}
		}
	}

	// DROP EVENT

	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
		ItemStack itemstack = event.getItemDrop().getItemStack();
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(DropEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(itemstack, Slot.FLOOR)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), itemstack, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), mainHand, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), offHand, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), helmet, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), chestplate, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), leggings, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new DropEvent(event.getPlayer(), boots, event.getItemDrop());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}
		}
	}

	// ITEM BREAK EVENT

	@EventHandler
	public void onPlayerItemBreakEvent(PlayerItemBreakEvent event) {
		ItemStack itemstack = event.getBrokenItem();
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(ItemBreakEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), mainHand, itemstack));
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), offHand, itemstack));
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), helmet, itemstack));
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), chestplate, itemstack));
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), leggings, itemstack));
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ItemBreakEvent(event.getPlayer(), boots, itemstack));
			}
		}
	}

	// ITEM CONSUME EVENT

	@EventHandler
	public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
		ItemStack itemstack = event.getItem();
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(ItemBreakEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), mainHand, itemstack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), offHand, itemstack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), helmet, itemstack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), chestplate, itemstack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), leggings, itemstack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				ItemConsumeEvent temp = new ItemConsumeEvent(event.getPlayer(), boots, itemstack);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
				event.setItem(temp.getItemStack());
			}
		}
	}

	// ITEM HELD EVENT
	
	@EventHandler
	public void onPlayerItemHeldEvent(PlayerChangedMainHandEvent event) {
		System.out.println("HELLO");
	}

	@EventHandler
	public void onPlayerItemHeldEvent(PlayerItemHeldEvent event) {
		System.out.println("TEST");
		int newSlot = event.getNewSlot(), oldSlot = event.getPreviousSlot();
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack current = inventory.getItem(newSlot), old = inventory.getItem(oldSlot);
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(SwitchItemEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new SwitchItemEvent(event.getPlayer(), old, current);
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}
		}
	}

	// PLAYER PICK UP ITEM EVENT
	// PICKING UP AN ITEM EVENT

	@EventHandler
	public void onPickupItemEvent(EntityPickupItemEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		PlayerInventory inventory = player.getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack pickup = event.getItem().getItemStack();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(PickUpEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(pickup, Slot.FLOOR)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}  else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}
		}
		// TODO: FIX PICKING UP
		events = _EVENTS.get(PickUpItemEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				CancellableEvent temp = new PickUpItemEvent(player, event.getItem());
				run.accept(temp);
				event.setCancelled(temp.isCancelled());
			}
		}
	}

	// TOGGLE SNEAK EVENT

	@EventHandler
	public void onToggleSneakEvent(PlayerToggleSneakEvent event) {
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(ToggleSneakEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), mainHand));
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), offHand));
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), helmet));
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), chestplate));
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), leggings));
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSneakEvent(event.getPlayer(), boots));
			}
		}
	}

	// TOGGLE SPRINT EVENT

	@EventHandler
	public void onToggleSprintEvent(PlayerToggleSprintEvent event) {
		PlayerInventory inventory = event.getPlayer().getInventory();
		ItemStack mainHand = inventory.getItemInMainHand();
		ItemStack offHand = inventory.getItemInOffHand();
		ItemStack helmet = inventory.getHelmet();
		ItemStack chestplate = inventory.getChestplate();
		ItemStack leggings = inventory.getLeggings();
		ItemStack boots = inventory.getBoots();
		Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(ToggleSprintEvent.class);
		for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
			if (tests.apply(mainHand, Slot.MAIN_HAND)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), mainHand));
			} else if (tests.apply(offHand, Slot.OFF_HAND)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), offHand));
			} else if (tests.apply(helmet, Slot.HELMET)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), helmet));
			} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), chestplate));
			} else if (tests.apply(leggings, Slot.LEGGINGS)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), leggings));
			} else if (tests.apply(boots, Slot.BOOTS)) {
				Consumer<Event> run = events.get(tests);
				run.accept(new ToggleSprintEvent(event.getPlayer(), boots));
			}
		}
	}

	// ENTITY DAMAGE EVENT

	@EventHandler
	public void onEntityDamageEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			PlayerInventory inventory = ((Player) event.getDamager()).getInventory();
			ItemStack mainHand = inventory.getItemInMainHand();
			ItemStack offHand = inventory.getItemInOffHand();
			ItemStack helmet = inventory.getHelmet();
			ItemStack chestplate = inventory.getChestplate();
			ItemStack leggings = inventory.getLeggings();
			ItemStack boots = inventory.getBoots();
			Map<BiFunction<ItemStack, Slot, Boolean>, Consumer<Event>> events = _EVENTS.get(DamageEntityEvent.class);
			for (BiFunction<ItemStack, Slot, Boolean> tests : events.keySet()) {
				if (tests.apply(mainHand, Slot.MAIN_HAND)) {
					Consumer<Event> run = events.get(tests);
					CancellableEvent temp = new DamageEntityEvent(((Player) event.getDamager()), mainHand,
							event.getEntity());
					run.accept(temp);
					event.setCancelled(temp.isCancelled());
				} else if (tests.apply(offHand, Slot.OFF_HAND)) {
					Consumer<Event> run = events.get(tests);
					CancellableEvent temp = new DamageEntityEvent(((Player) event.getDamager()), offHand,
							event.getEntity());
					run.accept(temp);
					event.setCancelled(temp.isCancelled());
				} else if (tests.apply(helmet, Slot.HELMET)) {
					Consumer<Event> run = events.get(tests);
					CancellableEvent temp = new DamageEntityEvent(((Player) event.getDamager()), helmet,
							event.getEntity());
					run.accept(temp);
					event.setCancelled(temp.isCancelled());
				} else if (tests.apply(chestplate, Slot.CHESTPLATE)) {
					Consumer<Event> run = events.get(tests);
					CancellableEvent temp = new DamageEntityEvent(((Player) event.getDamager()), chestplate,
							event.getEntity());
					run.accept(temp);
					event.setCancelled(temp.isCancelled());
				} else if (tests.apply(leggings, Slot.LEGGINGS)) {
					Consumer<Event> run = events.get(tests);
					CancellableEvent temp = new DamageEntityEvent(((Player) event.getDamager()), leggings,
							event.getEntity());
					run.accept(temp);
					event.setCancelled(temp.isCancelled());
				} else if (tests.apply(helmet, Slot.BOOTS)) {
					Consumer<Event> run = events.get(tests);
					CancellableEvent temp = new DamageEntityEvent(((Player) event.getDamager()), boots,
							event.getEntity());
					run.accept(temp);
					event.setCancelled(temp.isCancelled());
				}
			}
		}
	}
}
