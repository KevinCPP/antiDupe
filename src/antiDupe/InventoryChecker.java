package antiDupe;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryChecker {
	
	private HashMap<Material, Integer> itemList;
	
	//prevent default constructor from being used:
	@SuppressWarnings("unused")
	private InventoryChecker() {}
	
	/*
	 * This constructor accepts a filesystem object, and will use that to read the Yml file that
	 * contains all of the items and quantities to check for, and that will be used to check
	 * inventories.
	 */
	public InventoryChecker(FileSystem fileSystem) {
		itemList = fileSystem.getItemsList();
	}
	
	/*
	 * This constructor accepts a hashmap of Materials and Integers. The keys in this map
	 * (materials) will be what the inventorychecker will check for in the inventory. The
	 * values (integers) in this map will be the quantities that set off alerts. 
	 */
	public InventoryChecker(HashMap<Material, Integer> items) {
		itemList = items;
	}
	
	/*
	 * This function checks the inventory passed as a parameter to see if it contains any
	 * materials over the set quantities, and returns true if so. The materials and quantities
	 * to check for were passed during construction either as a FileSystem or a regular HashMap.
	 */
	public boolean checkInventory(Inventory inv) {
		//cannot check inventory if it is null
		if(inv == null)
			return false;
		
		//tally all of the items in the inventory, and put them in the hashmap
		HashMap<Material, Integer> inventoryItems = tallyItems(inv);
		
		//loop through every entry in the itemList (hashmap of items to be checked)
		//and see if the player has any of the materials in a quantity that is 
		//greater than the value set in the config file
		for(Entry<Material, Integer> itemListEntry : itemList.entrySet()) {
			//this will be equal to null if the item is not in their inventory
			Integer i = inventoryItems.get(itemListEntry.getKey());
			
			//if it's equal to null, go on to the next item
			if(i == null)
				continue;
			
			//if an item is found, return true, they have a material in greater
			//quantity than listed in the config file.
			if(i > itemListEntry.getValue())
				return true;			
		}
		
		//return false, nothing was found
		return false;
	}
	
	/*
	 * This function will accept an Inventory, and it will return a HashMap containing
	 * each unique material found in the inventory as the keys, and the total amount of
	 * the material in the inventory as the value:
	 */
	private HashMap<Material, Integer> tallyItems(Inventory inv){
		//make sure the inventory passed isn't null, if so, return an empty container
		if(inv == null)
			return new HashMap<Material, Integer>();
		
		//this will store a list of each unique material in the inventory, and
		//keep count of how many are in the inventory
		HashMap<Material, Integer> tally = new HashMap<Material, Integer>();
		
		//loop through every itemstack in the inventory
		for(ItemStack item : inv) {
			//if the item is null, go to next iteration
			if(item == null)
				continue;
			
			//If this material is already in the map, the value (amount counted so far)
			//will be stored in the Integer, otherwise it will be equal to null
			Integer i = tally.get(item.getType());
			
			//if the integer was null, the material hasn't been counted yet, so it can be
			//added to the map; otherwise we add the amount in this itemstack to the amounts
			//previously counted:
			if(i == null) {
				tally.put(item.getType(), item.getAmount());
			} else {
				tally.put(item.getType(), item.getAmount() + i);
			}
			
		}
		
		//return the tally hashmap, the way this function works, if there were no valid 
		//itemstacks in the inventory, it will still only return an empty map.
		return tally;
	}
	
	
}
