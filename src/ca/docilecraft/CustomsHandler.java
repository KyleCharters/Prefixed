package ca.docilecraft;

import java.util.UUID;

import javax.swing.Timer;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import ca.docilecraft.fetchers.NameFetcher;
import ca.docilecraft.fetchers.UUIDFetcher;

public class CustomsHandler{
	private static FileConfiguration players = PrefixedConfig.getPlayers();
	
	/*
	 * PLAYER
	 */
	
	protected static UUID playerStartsWithExists(String player){
		for(String key : players.getKeys(false)){
			String name = players.getConfigurationSection(key).getString("name");
			if(name != null && StringUtil.startsWithIgnoreCase(name, player) && key.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")){
				return UUID.fromString(key);
			}
		}
		return null;
	}
	
	protected static UUID playerExists(String player){
		for(String key : players.getKeys(false)){
			String name = players.getConfigurationSection(key).getString("name");
			if(name != null && name.equalsIgnoreCase(player) && key.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")){
				return UUID.fromString(key);
			}
		}
		return null;
	}
	
	protected static String playerName(UUID uuid){
		return getPlayer(uuid).getString("name");
	}
	
	/*
	 * PREFIX
	 */
	
	protected static String getPrefix(Player player){
		ConfigurationSection section = getPlayer(player.getUniqueId());
		return section == null ? null : section.getString("prefix");
	}
	
	protected static String getPrefix(UUID uuid){
		ConfigurationSection section = getPlayer(uuid);
		return section == null ? null : section.getString("prefix");
	}
	
	protected static String getPrefix(String name){
		ConfigurationSection section = getPlayer(name);
		return section == null ? null : section.getString("prefix");
	}
	
	protected static void setPrefix(Player player, String prefix){
		getPlayer(player.getUniqueId()).set("prefix", prefix);
		PrefixedConfig.savePlayers();
	}
	
	protected static void setPrefix(UUID uuid, String prefix){
		getPlayer(uuid).set("prefix", prefix);
		PrefixedConfig.savePlayers();
	}
	
	protected static void setPrefix(String name, String prefix){
		getPlayer(name).set("prefix", prefix);
		PrefixedConfig.savePlayers();
	}
	
	/*
	 * SUFFIX
	 */
	
	protected static String getSuffix(Player player){
		ConfigurationSection section = getPlayer(player.getUniqueId());
		return section == null ? null : section.getString("suffix");
	}
	
	protected static String getSuffix(UUID uuid){
		ConfigurationSection section = getPlayer(uuid);
		return section == null ? null : section.getString("suffix");
	}
	
	protected static String getSuffix(String player){
		ConfigurationSection section = getPlayer(player);
		return section == null ? null : section.getString("suffix");
	}
	
	protected static void setSuffix(Player player, String suffix){
		getPlayer(player.getUniqueId()).set("suffix", suffix);
		PrefixedConfig.savePlayers();
	}
	
	protected static void setSuffix(UUID uuid, String suffix){
		getPlayer(uuid).set("suffix", suffix);
		PrefixedConfig.savePlayers();
	}
	
	protected static void setSuffix(String name, String suffix){
		getPlayer(name).set("suffix", suffix);
		PrefixedConfig.savePlayers();
	}
	
	/*
	 * COLOR
	 */
	
	protected static String getColor(Player player){
		ConfigurationSection section = getPlayer(player.getUniqueId());
		return section == null ? null : section.getString("color");
	}
	
	protected static String getColor(UUID uuid){
		ConfigurationSection section = getPlayer(uuid);
		return section == null ? null : section.getString("color");
	}
	
	protected static String getColor(String name){
		ConfigurationSection section = getPlayer(name);
		return section == null ? null : section.getString("color");
	}
	
	protected static void setColor(Player player, String color){
		getPlayer(player.getUniqueId()).set("color", color.toUpperCase());
		PrefixedConfig.savePlayers();
		PrefixedListener.TabListener.reloadTabList();
	}
	
	protected static void setColor(UUID uuid, String color){
		getPlayer(uuid).set("color", color.toUpperCase());
		PrefixedConfig.savePlayers();
		PrefixedListener.TabListener.reloadTabList();
	}
	
	protected static void setColor(String name, String color){
		getPlayer(name).set("color", color.toUpperCase());
		PrefixedConfig.savePlayers();
		PrefixedListener.TabListener.reloadTabList();
	}
	
	/*
	 * UTILITY
	 */
	
	private static ConfigurationSection getPlayer(UUID uuid){
		return players.getConfigurationSection(uuid.toString());
	}
	
	private static ConfigurationSection getPlayer(String name){
		for(String key : players.getKeys(false)){
			ConfigurationSection section = players.getConfigurationSection(key);
			if(section.contains(name) && section.getString("name").equalsIgnoreCase(name)){
				Prefixed.log(3, "Player found");
				return section;
			}
		}
		Prefixed.log(3, "Player not found");
		return null;
	}
	
	protected static boolean setPlayer(String name, String prefix, String suffix, String color){
		UUID uuid = null;
		
		try{
			uuid = UUIDFetcher.getUUIDOf(name);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(uuid == null)
			return false;
		
		//Create section
		ConfigurationSection section = players.createSection(uuid.toString());
		
		//Set name
		section.set("name", name);
		//Set prefix
		if(prefix != null){
			section.set("prefix", prefix);
		}
		//Set suffix
		if(suffix != null){
			section.set("suffix", suffix);
		}
		//Set color
		if(color != null && PColor.isValidColor(color)){
			section.set("color", color.toUpperCase());
			PrefixedListener.TabListener.reloadTabList();
		}
		
		//Save players config
		PrefixedConfig.savePlayers();
		
		return true;
	}
	
	private static ConfigurationSection renamePlayer(ConfigurationSection section, String uuid, String name){
		//Create section
		ConfigurationSection newSection = players.createSection(uuid);
		
		//Set name
		newSection.set("name", name);
		
		//Copy keys
		for(String key : section.getKeys(false)){
			if(key.matches("(?i)\\b(prefix|suffix|color)\\b"))
				newSection.set(key.toLowerCase(), section.get(key));
		}
		
		//Delete old section
		players.set(section.getName(), null);
		
		return newSection;
	}
	
	/*
	 * PLAYER CHECKING UTIL
	 */
	
	static int time = 0;
	protected static void checkPlayers(){
		try{
			//Check if config is empty
			if(players.getKeys(false).isEmpty()){
				Prefixed.log(0, "Players file is empty");
				return;
			}
			
			//Start timer
			Timer timer = new Timer(100, (l) -> {
				time++;
			});
			timer.start();
			
			//Check players
			Prefixed.log(0, "Checking player uuids");
			
			for(String sectionName : players.getKeys(false)){
				ConfigurationSection section = players.getConfigurationSection(sectionName);
				
				checkPlayer(section);
			}
			
			//Save config
			PrefixedConfig.savePlayers();
			
			//Stop timer and reset
			timer.stop();
			Prefixed.log(0, "Finished checking player uuids, Time taken: "+String.valueOf((double)time/10)+" seconds.");
			time = 0;
		}catch(Exception e){
			e.printStackTrace();
			Prefixed.log(3, "Error checking player uuids");
		}
	}
	
	protected static void checkPlayer(ConfigurationSection section) throws Exception{
		String sectionName = section.getName();
		String currentName = section.getString("name");
		
		if(sectionName.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")){
			String playerName = NameFetcher.getUsernameOf(UUID.fromString(sectionName));
			
			if(currentName == null){
				section.set("name", playerName);
				Prefixed.log(0, "UUID \""+sectionName+"\" has the player name \""+playerName+"\"");
			}else if(!currentName.equals(playerName)){
				section.set("name", playerName);
				Prefixed.log(0, "Player \""+currentName+"\" has updated their player name to \""+playerName+"\"");
			}
		}else if(currentName != null){
			UUID playerUUID = UUIDFetcher.getUUIDOf(currentName);
			
			if(playerUUID != null){
				//Rename section
				section = renamePlayer(section, playerUUID.toString(), currentName);
				
				Prefixed.log(0, "Player \""+currentName+"\"'s UUID was updated to \""+playerUUID.toString()+"\"");
			}else{
				Prefixed.log(1, "Neither UUID or Name is valid for player \""+currentName+"\"");
			}
		}else{
			UUID playerUUID = UUIDFetcher.getUUIDOf(sectionName);
			
			if(playerUUID != null){
				//Rename section
				section  = renamePlayer(section, playerUUID.toString(), NameFetcher.getUsernameOf(playerUUID));
				
				//Delete old section
				players.set(sectionName, null);
				
				Prefixed.log(0, "Player \""+sectionName+"\"'s UUID was updated to \""+playerUUID.toString()+"\"");
			}else{
				Prefixed.log(1, "Player \""+sectionName+"\" is not registered.");
			}
		}
	}
}