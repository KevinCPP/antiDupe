package antiDupe;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileSystem {
	
	private Plugin plugin;
	private File itemsYml;
	private FileConfiguration itemsConfigFile;
	
	/*
	 * this function saves any changes to the itemsYml
	 */
	public void saveFile() {
		try {
			itemsConfigFile.save(itemsYml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * this adds an item to the item YML
	 */
	public void addItemElement(Material material, int quantity) {
		itemsConfigFile.set(material.toString(), quantity);
	}
	
	/*
	 * In the default constructor, the plugin will default
	 * to the main class, and the default filename for the
	 * items will be items.yml
	 */
	public FileSystem() {
		plugin = Main.getPlugin(Main.class);
		itemsYml = new File(plugin.getDataFolder() + "/items.yml");
		itemsConfigFile = YamlConfiguration.loadConfiguration(itemsYml);
	}
	
	public FileSystem(Plugin plugin, String itemsYmlFileName) {
		this.plugin = plugin;
		itemsYml = new File(plugin.getDataFolder() + "/" + itemsYmlFileName);
		itemsConfigFile = YamlConfiguration.loadConfiguration(itemsYml);
	}
	
	
	/*
	 * Retrieves a list of items and their quantities from the
	 * items.yml file. Returns this data as a hashmap<Material, Integer>
	 * where the Material is the material, and the Integer is the quantity.
	 * This function does not return null.
	 */
	public HashMap<Material, Integer> getItemsList(){
		
		HashMap<Material, Integer> itemList = new HashMap<>();
		
		//loop through every key in the items.yml file
		for(String s : itemsConfigFile.getKeys(true)) {
			//see if the key is a valid material
			Material m = Material.getMaterial(s);
			if(m != null) {
				//if it was a valid material, get the amount listed beside of it
				int amount = itemsConfigFile.getInt(s);
				
				//if the amount is less than 1, set it equal to 1 and send an error message
				if(amount < 1) {
					utility.sendLogMessage("&cError: The quantity listed beside of: " + m.name() + " in items.yml cannot be less than 1. It will be treated as if the quantity was 1.");
					amount = 1;
				}
				
				//i will be equal to null if this material is not already listed in the HashMap, and if it's equal to null,
				//we add the material to the hashmap and the quantity retireved from the file. This makes it so that if the
				//same material is listed twice, only the first instance will be used.
				Integer i = itemList.get(m);
				if(i == null) {
					itemList.put(m, amount);
				} else {
					//send an error message because if the material was already in the hashmap, then this is a second instance of the same material.
					utility.sendLogMessage("&cError: The material: " + m.name() + " in items.yml could be listed twice. If it is listed twice, only the first instance will be used.");
				}
				
			}
		}
		
		return itemList;
	}
}
