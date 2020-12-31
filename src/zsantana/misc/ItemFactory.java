package zsantana.misc;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
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

	public static ItemStack createItem(Material mat, int amount, int byteData, String displayName) {
		ItemStack itemStack = new ItemStack(mat, amount, (byte) byteData);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + displayName));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}

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
}
