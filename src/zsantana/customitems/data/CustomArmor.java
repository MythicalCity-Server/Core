package zsantana.customitems.data;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.bukkit.inventory.ItemStack;

import zsantana.customitems.CustomItemEventHandler;
import zsantana.customitems.events.Event;

public abstract class CustomArmor {

	private static CustomItemEventHandler _EVENT_HANDLER;

	/**
	 * @param eventHandler The Event Handler to register events on to during load
	 */
	public static void setEventHandler(CustomItemEventHandler eventHandler) {
		_EVENT_HANDLER = eventHandler;
	}

	public CustomArmor() {
		assignListening();
	}

	/**
	 * Registers a new event to be considered with the event type
	 * 
	 * @param eventType The event you would like the action to run on
	 * @param action    The action to run when this event is triggered with this
	 *                  custom item
	 */
	public final void registerAction(Class<? extends Event> eventType, Consumer<Event> action) {
		_EVENT_HANDLER.register(eventType, this, action);
	}

	/**
	 * Tests if this custom item is indeed the itemstack provided
	 * 
	 * @param item An itemstack to check
	 * @param slot The slot of the armor
	 * @return If this custom item is the itemstack provided
	 */
	public final boolean isApplicable(ItemStack item, Slot slot) {
		return slot.isAllowed(getSlot()) && getItem().isSimilar(item);
	}

	/**
	 * Tests if this custom item is indeed the itemstack provided
	 * 
	 * @param item An itemstack to check
	 * @param slot The slot of the armor
	 * @return If this custom item is the itemstack provided
	 */
	public final boolean isApplicable(ItemStack item, Slot customSlot, Slot slot) {
		return slot.isAllowed(customSlot) && getItem().isSimilar(item);
	}

	/**
	 * Returns the item to use when checking if an item is applicable
	 * 
	 * @return An itemstack representing this custom item
	 */
	public abstract ItemStack getItem();

	/**
	 * Returns the slot that this item should be in use for
	 * 
	 * @return A slot representing the use case of this item
	 */
	public abstract Slot getSlot();

	@SuppressWarnings("unchecked")
	private final void assignListening() {
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Listening.class)) {
				if (method.getParameterCount() == 1) {
					if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
						Listening annotation = method.getAnnotation(Listening.class);
						Slot customSlot = annotation.slot();
						if (customSlot.equals(Slot.NA)) {
							_EVENT_HANDLER.register((Class<? extends Event>) method.getParameterTypes()[0], this,
									(event) -> {
										try {
											method.invoke(this, method.getParameterTypes()[0].cast(event));
										} catch (Exception e) {
											e.printStackTrace();
										}
									});
						} else {
							_EVENT_HANDLER.register((Class<? extends Event>) method.getParameterTypes()[0], this,
									customSlot, (event) -> {
										try {
											method.invoke(this, method.getParameterTypes()[0].cast(event));
										} catch (Exception e) {
											e.printStackTrace();
										}
									});
						}
					}
				}
			}
		}
	}
}
