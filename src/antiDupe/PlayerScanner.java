package antiDupe;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerScanner extends BukkitRunnable{
	
	private InventoryChecker inventoryChecker;
	
	//prevent usage of the default constructor, this class depends on
	//a valid InventoryChecker being passed
	@SuppressWarnings("unused")
	private PlayerScanner() {}
	
	public PlayerScanner(InventoryChecker inventoryChecker) {
		this.inventoryChecker = inventoryChecker;
	}
	
	@Override
	public void run() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			//get their inventory/echest and have the inventorychecker
			//see if there are any suspicious quantities of materials in it
			inventoryChecker.checkInventory(player.getInventory());
			inventoryChecker.checkInventory(player.getEnderChest());
		}
	}
}
