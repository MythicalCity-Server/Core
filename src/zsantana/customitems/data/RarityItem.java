package zsantana.customitems.data;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.DamageEntityEvent;
import zsantana.customitems.events.DropEvent;
import zsantana.customitems.events.Event;
import zsantana.customitems.events.InteractEvent;
import zsantana.customitems.events.ItemBreakEvent;
import zsantana.customitems.events.ItemConsumeEvent;
import zsantana.customitems.events.PickUpEvent;
import zsantana.customitems.events.PickUpItemEvent;
import zsantana.customitems.events.SwitchItemEvent;
import zsantana.customitems.events.ToggleSneakEvent;
import zsantana.customitems.events.ToggleSprintEvent;
import zsantana.misc.ItemFactory;

/**
 * All custom items extend this class and implement methods using the @Listening
 * annotation The first and only parameter in those methods must be classes that
 * extend Event
 * 
 * @author Zackary Santana
 *
 */
public abstract class RarityItem extends CustomItem {

	private ArrayList<String> _DESCRIPTION;

	private ItemStack _item;

	public RarityItem(Material material, String name, String description, Rarity rarity) {
		super();
		int i = 0;
		for (String desc : description.split("\\n")) {
			this._DESCRIPTION.add(i++, desc);
		}
		this._DESCRIPTION.add("  ");
		this._DESCRIPTION.add(rarity.getColor() + "&o" + rarity.getName() + " Item");
		this._item = ItemFactory.createItem(material, 1, rarity.getColor() + name, _DESCRIPTION);
	}

	/**
	 * Returns the slot that this item should be in use for
	 * 
	 * @return A slot representing the use case of this item
	 */
	public abstract Slot getSlot();

	/**
	 * Returns the item to use when checking if an item is applicable
	 * 
	 * @return An itemstack representing this custom item
	 */
	public final ItemStack getItem() {
		return this._item;
	}

	@SuppressWarnings("unchecked")
	void assignListening() {
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Listening.class)) {
				if (method.getParameterCount() == 1) {
					if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
						Class<? extends Event> type = (Class<? extends Event>) method.getParameterTypes()[0];
						_EVENT_HANDLER.register(type, this, (event) -> {
							try {
								method.invoke(this, method.getParameterTypes()[0].cast(event));
							} catch (Exception e) {
								e.printStackTrace();
							}
						});

						Listening annotation = method.getAnnotation(Listening.class);
						String desc = annotation.description();
						if (!desc.equals("")) {
							if (_DESCRIPTION == null) {
								_DESCRIPTION = new ArrayList<>();
							}

							String eventType = "&e";

							if (!annotation.itemAbility().equals("")) {
								eventType = annotation.itemAbility();
							} else if (type == DamageEntityEvent.class) {
								eventType = "Attack Ability";
							} else if (type == DropEvent.class) {
								eventType = "Drop Ability";
							} else if (type == InteractEvent.class) {
								eventType = "Interact Ability";
							} else if (type == ItemBreakEvent.class) {
								eventType = "Item Break Ability";
							} else if (type == ItemConsumeEvent.class) {
								eventType = "Item Consume Ability";
							} else if (type == PickUpEvent.class) {
								eventType = "Picked up Ability";
							} else if (type == PickUpItemEvent.class) {
								eventType = "Pick up Ability";
							} else if (type == SwitchItemEvent.class) {
								eventType = "Switch Item Ability";
							} else if (type == ToggleSneakEvent.class) {
								eventType = "Sneak Ability";
							} else if (type == ToggleSprintEvent.class) {
								eventType = "Sprint Ability";
							}

							int itemLoreLength = 40;
							_DESCRIPTION.add(" ");
							if (desc.length() + eventType.length() > itemLoreLength) {
								int space = getClosestSpace(desc, itemLoreLength - eventType.length());
								_DESCRIPTION.add("&e" + eventType + ": &7" + desc.substring(0, space));
								while (space > 0) {
									desc = desc.substring(space, desc.length());
									if (desc.length() > itemLoreLength) {
										space = getClosestSpace(desc, itemLoreLength);
										_DESCRIPTION.add("&7" + desc.substring(0, space));
										System.out.println(space);
									} else {
										_DESCRIPTION.add("&7" + desc);
										space = 0;
									}
								}
							} else {
								_DESCRIPTION.add("&e" + eventType + ": &7" + desc);
							}
						}
					}
				}
			}
		}
	}

	private int getClosestSpace(String str, int startingPoint) {
		char[] c = str.toCharArray();
		while (startingPoint >= 0) {
			if (c.length > startingPoint - 1 && c[startingPoint - 1] == ' ') {
				return startingPoint;
			}
			startingPoint--;
		}
		return startingPoint;
	}
}
