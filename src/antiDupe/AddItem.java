package antiDupe;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class AddItem implements TabExecutor{
	
	private FileSystem fileSystem;

	//disable default constructor
	@SuppressWarnings("unused")
	private AddItem() {}
	
	/*
	 * This constructor accepts a filesystem which is needed
	 */
	public AddItem(FileSystem fileSystem) {
		Main.getPlugin(Main.class).getCommand("addItem").setTabCompleter(this);
		this.fileSystem = fileSystem;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String noPermissionError = "Error: You don't have permission!";
		String badUsageError = "Error: Incorrect Usage | Usage: /addItem <Material> <quantity> (quantity cannot be below 1)";
		
		//make sure no arguments are null
		if(sender == null || cmd == null || label == null || args == null)
			return false;
		
		//make sure that the command is addItem
		if(!cmd.getName().equalsIgnoreCase("addItem"))
			return false;
		
		//checks to see if the sender is a player, and if they have permission to do this command
		boolean isPlayer = (sender instanceof Player);
		boolean hasPermission = sender.hasPermission("AntiDupe.addItem");
		
		//if it's a player, and they don't have the permission, they can't do the command.
		//if it's not a player, then it's the console, and doesn't need permission
		if(isPlayer && !hasPermission) {
			utility.sendErrorMsg(sender, noPermissionError);
			return false;
		}
		
		//this command requires two arguments
		if(args.length != 2) {
			utility.sendErrorMsg(sender, badUsageError);
			return false;
		}
		
		//make sure neither argument is null
		if(args[0] == null || args[1] == null) {
			utility.sendErrorMsg(sender, badUsageError);
			return false;
		}
		//get the material from the first argument
		Material material = Material.getMaterial(args[0].toUpperCase());
		
		//make sure first argument was a valid material
		if(material == null) {
			utility.sendErrorMsg(sender, badUsageError);
			return false;
		}
		
		//make sure second argument is a valid quantity
		Integer quantity = null;
		try {
			quantity = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) { 
			utility.sendErrorMsg(sender, badUsageError);
			return false;	
		}
		
		//the quantity can't be less than 1
		if(quantity < 1) {
			utility.sendErrorMsg(sender, badUsageError);
			return false;
		}
		
		//all of these checks passed, add the item and quantity
		fileSystem.addItemElement(material, quantity);
		
		//we added the item and quantity to the file, so return true.
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		List<String> results = new ArrayList<>();
		
		if(args.length == 0) {
			for(Material mat : Material.values())
				results.add(mat.name());
		}
		if(args.length == 1) {
			for(Material mat : Material.values()) {
				if(mat.name().toLowerCase().startsWith(args[0].toLowerCase()))
					results.add(mat.name());
			}
		}
		
		return results;
	}
}
