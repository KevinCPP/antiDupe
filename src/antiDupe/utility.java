package antiDupe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class utility {
	
	//prevent usage of the default constructor
	@SuppressWarnings("unused")
	private utility() {}
	
	/*
	 * This function sends the message to the sender, but the message will
	 * translate the color codes using &. Example: sendMessageC(sender, "&4hello")
	 * would send the message, "hello" in red to the sender.
	 */
	public static void sendMessageC(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	/*
	 * This function sends the message to the sender, but does not support
	 * color codes in the message, it will always be in Light Red (&c color code).
	 * Usage: sendErrorMsg(sender, "hello") would send the message, "hello" to the
	 * sender in light red. This function should be used for error messages, so that
	 * the color of error messages can be changed in one place.
	 */
	public static void sendErrorMsg(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.RED + message);
	}
	
	/*
	 * This function sends a message to the console, and supports color codes.
	 * Usage: SendLogMessage("hello") would print "hello" in the server's console
	 */
	public static void sendLogMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "Anti-Dupe | " + message));
	}
	
}
