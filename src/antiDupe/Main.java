package antiDupe;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin{
	
	//filename of the yml file that contains all of the materials and quantities
	final String itemsYmlFileName = "items.yml";
	//filesystem to be used all throughout this plugin
	final FileSystem fileSystem = new FileSystem(this, itemsYmlFileName);
	
	/*
	 * This function executes when the plugin is loaded/enabled,
	 * due to the server starting, after a restart, or after a
	 * plugin reload, etc.
	 */
	@Override
	public void onEnable() {
		//this will be the inventoryChecker object passed to ContainerScanner and playerScanner. It's
		//best to get this here because it can be initialized with the FileSystem
		InventoryChecker inventoryChecker = new InventoryChecker(fileSystem);
		
		//register containerScanner event because it uses InventoryCloseEvent from bukkit.
		getServer().getPluginManager().registerEvents(new ContainerScanner(inventoryChecker), this);
		
		//Register PlayerScanner as a bukkit task so that it will execute the run() function on 
		//regular intervals. This is a synchronous task.
		@SuppressWarnings("unused")
		BukkitTask playerScan = new PlayerScanner(inventoryChecker).runTaskTimer(this, 500L, 100L);
		
		//set AddItem as the executor for this command so that it's onCommand() function is used
		//when a player types a command.
		this.getCommand("addItem").setExecutor(new AddItem(fileSystem));
	}
	
	/*
	 * This function executes when the plugin is disabled due to
	 * the server shutting down, restarting, reloading plugins, etc.
	 */
	@Override
	public void onDisable() {
		//save any changes to the items Yml file
		fileSystem.saveFile();
	}
	
	
	
	
}
