package zsantana.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public class Log {
	
	private static String _PREFIX;
	
	static {
		_PREFIX = "&b";
	}
	
	public static void m(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', _PREFIX + message));
	}
	
	public static void s(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', "&a" + message));
	}

	public static void e(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', "&c" + message));
	}
	
	public static void m(Entity entity, String message) {
		entity.sendMessage(ChatColor.translateAlternateColorCodes('&', _PREFIX + message));
	}
}
