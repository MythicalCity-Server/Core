package zsantana.misc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemFactory {

	public static ItemStack createItem(Material mat, int amount, String displayName) {
		ItemStack itemStack = new ItemStack(mat, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + displayName));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(Material mat, int amount, String displayName, String... lores) {
		ItemStack itemStack = new ItemStack(mat, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + displayName));
		ArrayList<String> fLores = new ArrayList<>();
		for (String lore : lores) {
			fLores.add(ChatColor.translateAlternateColorCodes('&', "&7" + lore));
		}
		itemMeta.setLore(fLores);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static ItemStack createItem(Material mat, int amount, String displayName, ArrayList<String> lores) {
		ItemStack itemStack = new ItemStack(mat, amount);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + displayName));
		ArrayList<String> fLores = new ArrayList<>();
		for (String lore : lores) {
			fLores.add(ChatColor.translateAlternateColorCodes('&', "&7" + lore));
		}
		itemMeta.setLore(fLores);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Material mat, int amount, int byteData, String displayName) {
		ItemStack itemStack = new ItemStack(mat, amount, (byte) byteData);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + displayName));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Material mat, int amount, int byteData, String displayName, String... lores) {
		ItemStack itemStack = new ItemStack(mat, amount, (byte) byteData);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + displayName));
		ArrayList<String> fLores = new ArrayList<>();
		for (String lore : lores) {
			fLores.add(ChatColor.translateAlternateColorCodes('&', "&7" + lore));
		}
		itemMeta.setLore(fLores);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

	public static boolean isApplicable(ItemStack item1, ItemStack item2) {
		if (item1 == null || item2 == null) {
			return false;
		}
		item1 = item1.clone();
		ItemMeta meta = item1.getItemMeta();
		if (meta instanceof Damageable && item2.getItemMeta() instanceof Damageable) {
			((Damageable) meta).setDamage(((Damageable) item2.getItemMeta()).getDamage());
			item1.setItemMeta(meta);
		}
		return item2.hasItemMeta() == item1.hasItemMeta()
				&& (item2.hasItemMeta() ? Bukkit.getItemFactory().equals(item2.getItemMeta(), item1.getItemMeta())
						: true);
	}
}
