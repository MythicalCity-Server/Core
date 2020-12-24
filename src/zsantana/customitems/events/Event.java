package zsantana.customitems.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * The main Event class that sub events extend
 * 
 * @author Zackary Santana
 *
 */
public class Event {

	private final Player _PLAYER;
	private final ItemStack _ITEMSTACK;

	/**
	 * Creates a new event
	 * 
	 * @param player    The player who did the event
	 * @param itemStack The itemstack that is involved in the event
	 */
	public Event(Player player, ItemStack itemStack) {
		this._PLAYER = player;
		this._ITEMSTACK = itemStack;
	}

	/**
	 * @return The player that did the event
	 */
	public Player getPlayer() {
		return this._PLAYER;
	}

	/**
	 * @return The itemstack that is involved in the event
	 */
	public ItemStack getItemStack() {
		return this._ITEMSTACK;
	}

	/**
	 * A cancellable event extends this instead of event, so that it may be
	 * cancelled
	 * 
	 * @author Zackary Santana
	 *
	 */
	public static class CancellableEvent extends Event {

		private boolean _cancelled;

		/**
		 * Initializes cancelled as false
		 * 
		 * @param player    The player who did the event
		 * @param itemStack The itemstack that is involved in the event
		 */
		public CancellableEvent(Player player, ItemStack itemStack) {
			super(player, itemStack);
			this._cancelled = false;
		}

		/**
		 * @return If the event is cancelled
		 */
		public boolean isCancelled() {
			return this._cancelled;
		}

		/**
		 * @param cancelled The new status of; if the event is cancelled
		 */
		public void setCancelled(boolean cancelled) {
			this._cancelled = cancelled;
		}
	}

	/**
	 * The position of the item during the event
	 * 
	 * @author Zackary Santana
	 *
	 */
	public static enum Position {

		CURRENT, OLD, HAND, FLOOR

	}
}
