package zsantana.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

/**
 * Used to do quick logs or send efficient messages to the player/console
 * 
 * @author Zackary Santana
 *
 */
public class Log {

	private static String _PREFIX;

	static {
		_PREFIX = "&b";
	}

	/**
	 * A standard message using the prefix defined in the class Sent to the console
	 * 
	 * @param message The message to send to the console with the prefix
	 */
	public static void m(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', _PREFIX + message));
	}

	/**
	 * A success message using a green prefix Sent to the console
	 * 
	 * @param message The message to send to the console
	 */
	public static void s(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', "&a" + message));
	}

	/**
	 * An error message using a red prefix Sent to the console
	 * 
	 * @param message The message to send to the console
	 */
	public static void e(String message) {
		System.out.println(ChatColor.translateAlternateColorCodes('&', "&c" + message));
	}

	/**
	 * A standard message using the prefix defined in the class Sent to the entity
	 * 
	 * @param message The message to send to the entity with the prefix
	 */
	public static void m(Entity entity, String message) {
		entity.sendMessage(ChatColor.translateAlternateColorCodes('&', _PREFIX + message));
	}

	/**
	 * A standard message using the prefix defined in the class Sent to the command sender
	 * 
	 * @param message The message to send to the command sender with the prefix
	 */
	public static void m(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', _PREFIX + message));
	}
}
