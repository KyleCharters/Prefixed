package ca.docilecraft;

import java.util.UUID;

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
	
	protected static UUID getUUIDStartsWith(String player){
		for(String key : players.getKeys(false)){
			String name = players.getConfigurationSection(key).getString("name");
			if(name != null && StringUtil.startsWithIgnoreCase(name, player) && key.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")){
				return UUID.fromString(key);
			}
		}
		return null;
	}
	
	protected static UUID getUUID(String player){
		for(String key : players.getKeys(false)){
			String name = players.getConfigurationSection(key).getString("name");
			if(name != null && name.equalsIgnoreCase(player) && key.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")){
				return UUID.fromString(key);
			}
		}
		return null;
	}
	
	protected static String getName(UUID uuid){
		return getPlayer(uuid).getString("name");
	}
	
	/*
	 * PREFIX
	 */
	
	protected static String getPrefix(Player player){
		return getPrefix(getPlayer(player.getUniqueId()));
	}
	
	protected static String getPrefix(UUID uuid){
		return getPrefix(getPlayer(uuid));
	}
	
	protected static String getPrefix(String name){
		return getPrefix(getPlayer(name));
	}
	
	private static String getPrefix(ConfigurationSection section){
		return section == null ? null : section.getString("prefix");
	}
	
	protected static void setPrefix(Player player, String prefix){
		setPrefix(getPlayer(player.getUniqueId()), prefix);
	}
	
	protected static void setPrefix(UUID uuid, String prefix){
		setPrefix(getPlayer(uuid), prefix);
	}
	
	protected static void setPrefix(String name, String prefix){
		setPrefix(getPlayer(name), prefix);
	}
	
	private static void setPrefix(ConfigurationSection section, String prefix){
		section.set("prefix", prefix);
		PrefixedConfig.savePlayers();
	}
	
	/*
	 * SUFFIX
	 */
	
	protected static String getSuffix(Player player){
		return getSuffix(getPlayer(player.getUniqueId()));
	}
	
	protected static String getSuffix(UUID uuid){
		return getSuffix(getPlayer(uuid));
	}
	
	protected static String getSuffix(String player){
		return getSuffix(getPlayer(player));
	}
	
	private static String getSuffix(ConfigurationSection section){
		return section == null ? null : section.getString("suffix");
	}
	
	protected static void setSuffix(Player player, String suffix){
		setSuffix(getPlayer(player.getUniqueId()), suffix);
	}
	
	protected static void setSuffix(UUID uuid, String suffix){
		setSuffix(getPlayer(uuid), suffix);
	}
	
	protected static void setSuffix(String name, String suffix){
		setSuffix(getPlayer(name), suffix);
	}
	
	private static void setSuffix(ConfigurationSection section, String suffix){
		section.set("suffix", suffix);
		PrefixedConfig.savePlayers();
	}
	
	/*
	 * COLOR
	 */
	
	protected static String getColor(Player player){
		return getColor(getPlayer(player.getUniqueId()));
	}
	
	protected static String getColor(UUID uuid){
		return getColor(getPlayer(uuid));
	}
	
	protected static String getColor(String name){
		return getColor(getPlayer(name));
	}
	
	private static String getColor(ConfigurationSection section){
		return section == null ? null : section.getString("color");
	}
	
	protected static void setColor(Player player, String color){
		setColor(player.getUniqueId(), color);
	}
	
	protected static void setColor(UUID uuid, String color){
		setColor(getPlayer(uuid), color);
	}
	
	protected static void setColor(String name, String color){
		setColor(getPlayer(name), color);
	}
	
	private static void setColor(ConfigurationSection section, String color){
		section.set("color", color.toUpperCase());
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
	
	protected static void checkPlayers(){
		try{
			//Check if config is empty
			if(players.getKeys(false).isEmpty()){
				Prefixed.log(0, "Players file is empty");
				return;
			}
			
			//Start timer
			long beginningTime = System.nanoTime() / 1000000000;
			
			//Check players
			Prefixed.log(0, "Checking player uuids");
			
			for(String sectionName : players.getKeys(false)){
				ConfigurationSection section = players.getConfigurationSection(sectionName);
				
				checkPlayer(section);
			}
			
			//Save config
			PrefixedConfig.savePlayers();
			
			//Stop timer
			long timeTaken = (System.nanoTime() / 1000000000) - beginningTime;
			Prefixed.log(0, "Finished checking player uuids, Time taken: "+timeTaken+" second(s).");
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