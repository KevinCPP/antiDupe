package antiDupe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class ContainerScanner implements Listener{
	
	private InventoryChecker inventoryChecker;
	
	//disable default constructor
	@SuppressWarnings("unused")
	private ContainerScanner() {}
	
	public ContainerScanner(InventoryChecker inventoryChecker) {
		this.inventoryChecker = inventoryChecker;
	}
	
	/*
	 * this runs anytime the InventoryCloseEvent is triggered, and it
	 * scans that inventory
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onClose(InventoryCloseEvent e) {
		
		//if it was an ender chest, don't check because ender chests will be scanned
		//regularly with the player's inventory.
		if(e.getInventory().getType().equals(InventoryType.ENDER_CHEST))
			return;
		
		//send inventory to get checked
		inventoryChecker.checkInventory(e.getInventory());
	}
}