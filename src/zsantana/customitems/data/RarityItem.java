package zsantana.customitems.data;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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

public abstract class RarityItem extends CustomItem {

	private final Material _MATERIAL;
	private final String _NAME, _PRE_DESC;
	private final Rarity _RARITY;

	private ArrayList<String> _description;
	private ItemStack _item;

	public RarityItem(Material material, String name, String description, Rarity rarity) {
		super();
		this._MATERIAL = material;
		this._NAME = name;
		this._PRE_DESC = description;
		this._RARITY = rarity;

		createItem();
	}

	public RarityItem(Material material, String name, String description, Rarity rarity, int startingDurability) {
		super();
		this._MATERIAL = material;
		this._NAME = name;
		this._PRE_DESC = description;
		this._RARITY = rarity;

		createItem();

		ItemMeta itemMeta = this._item.getItemMeta();
		if (itemMeta instanceof Damageable) {
			((Damageable) itemMeta).setDamage(startingDurability);
			this._item.setItemMeta(itemMeta);
		}
	}

	public RarityItem(Material material, String name, String description, Rarity rarity, int startingDurability,
			Color leatherArmorColor) {
		super();
		this._MATERIAL = material;
		this._NAME = name;
		this._PRE_DESC = description;
		this._RARITY = rarity;

		createItem();

		ItemMeta itemMeta = this._item.getItemMeta();
		System.out.println(this._item + "" + (this._item != null ? this._item.getType().name() : "NULL!"));
		if (startingDurability != 0) {
			if (itemMeta instanceof Damageable) {
				((Damageable) itemMeta).setDamage(startingDurability);
				this._item.setItemMeta(itemMeta);
			}
		}
		
		if (itemMeta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) itemMeta).setColor(leatherArmorColor);
			this._item.setItemMeta(itemMeta);
		}
		System.out.println(this._item + "" + (this._item != null ? this._item.getType().name() : "NULL!"));
		
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

	private final void createItem() {
		int i = 0;
		if (this._description == null) {
			this._description = new ArrayList<>();
		}
		for (String desc : this._PRE_DESC.split("\\n")) {
			this._description.add(i++, desc);
		}
		this._description.add("  ");
		this._description.add(this._RARITY.getColor() + "&o" + this._RARITY.getName() + " Armor");
		this._item = ItemFactory.createItem(this._MATERIAL, 1, this._RARITY.getColor() + this._NAME, this._description);
	}

	@SuppressWarnings("unchecked")
	void assignListening() {
		for (Method method : this.getClass().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Listening.class)) {
				if (method.getParameterCount() == 1) {
					if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
						Class<? extends Event> type = (Class<? extends Event>) method.getParameterTypes()[0];
						Listening annotation = method.getAnnotation(Listening.class);
						Slot customSlot = annotation.slot();
						if (customSlot.equals(Slot.NA)) {
							_EVENT_HANDLER.register(type, this, (event) -> {
								try {
									method.invoke(this, method.getParameterTypes()[0].cast(event));
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
						} else {
							_EVENT_HANDLER.register(type, this, customSlot, (event) -> {
								try {
									method.invoke(this, method.getParameterTypes()[0].cast(event));
								} catch (Exception e) {
									e.printStackTrace();
								}
							});
						}

						String desc = annotation.description();
						if (!desc.equals("")) {
							if (_description == null) {
								_description = new ArrayList<>();
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
							_description.add(" ");
							if (desc.length() + eventType.length() > itemLoreLength) {
								int space = getClosestSpace(desc, itemLoreLength - eventType.length());
								_description.add("&e" + eventType + ": &7" + desc.substring(0, space));
								while (space > 0) {
									desc = desc.substring(space, desc.length());
									if (desc.length() > itemLoreLength) {
										space = getClosestSpace(desc, itemLoreLength);
										_description.add("&7" + desc.substring(0, space));
										System.out.println(space);
									} else {
										_description.add("&7" + desc);
										space = 0;
									}
								}
							} else {
								_description.add("&e" + eventType + ": &7" + desc);
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
