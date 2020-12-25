package zsantana.customitems.data;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.bukkit.inventory.ItemStack;

import zsantana.customitems.CustomItemEventHandler;
import zsantana.customitems.events.Event;

/**
 * All custom items extend this class and implement methods using the @Listening annotation
 * The first and only parameter in those methods must be classes that extend Event
 * 
 * @author Zackary Santana
 *
 */
public abstract class CustomItem {
	
	private static CustomItemEventHandler _EVENT_HANDLER;
	
	/**
	 * @param eventHandler The Event Handler to register events on to during load
	 */
	public static void setEventHandler(CustomItemEventHandler eventHandler) {
		_EVENT_HANDLER = eventHandler;
	}
	
	public CustomItem() {
		assignListening();
	}
	
	/**
	 * Registers a new event to be considered with the event type
	 * 
	 * @param eventType The event you would like the action to run on
	 * @param action The action to run when this event is triggered with this custom item
	 */
	public final void registerAction(Class<? extends Event> eventType, Consumer<Event> action) {
		_EVENT_HANDLER.register(eventType, this, action);
	}
	
	/**
	 * Tests if this custom item is indeed the itemstack provided
	 * 
	 * @param item An itemstack to check
	 * @return If this custom item is the itemstack provided
	 */
	public final boolean isApplicable(ItemStack item) {
		return getItem().isSimilar(item);
	}
	
	/**
	 * Returns the item to use when checking if an item is applicable 
	 * 
	 * @return An itemstack representing this custom item
	 */
	public abstract ItemStack getItem();
	
	@SuppressWarnings("unchecked")
	private final void assignListening() {
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Listening.class)) {
				if (method.getParameterCount() == 1) {
					if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
						_EVENT_HANDLER.register((Class<? extends Event>) method.getParameterTypes()[0], this, (event) -> {
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
