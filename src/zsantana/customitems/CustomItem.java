package zsantana.customitems;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import zsantana.customitems.events.Event;

public abstract class CustomItem {
	
	private Settings _settings;
	
	@SafeVarargs
	public CustomItem(Class<? extends Event>... listenTo) {
		this._settings = new Settings(listenTo);
	}
	
	public CustomItem(List<Class<? extends Event>> listenTo) {
		this._settings = new Settings(listenTo);
	}

	@SafeVarargs
	public final void listenTo(Class<? extends Event>... listenTo) {
		for (Class<? extends Event> listen : listenTo) {
			this._settings._listeningTo.add(listen);
		}
	}

	public final void listenTo(List<Class<? extends Event>> listenTo) {
		this._settings._listeningTo.addAll(listenTo);
	}
	
	public final List<Class<? extends Event>> register() {
		return this._settings._listeningTo;
	}
	
	public final void runEvent(Event event, Class<? extends Event> eventType) {
		
	}

	public boolean isApplicable(ItemStack item) {
		return getItem().isSimilar(item);
	}
	
	public abstract ItemStack getItem();
	
	private class Settings {
		
		private final List<Class<? extends Event>> _listeningTo;
		
		@SafeVarargs
		public Settings(Class<? extends Event>... listenTo) {
			this._listeningTo = new ArrayList<>();
			for (Class<? extends Event> listen : listenTo) {
				this._listeningTo.add(listen);
			}
		}
		
		public Settings(List<Class<? extends Event>> listenTo) {
			this._listeningTo = new ArrayList<>();
			this._listeningTo.addAll(listenTo);
		}
	}
}
