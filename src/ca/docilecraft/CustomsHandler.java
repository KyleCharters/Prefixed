package ca.docilecraft;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.StringUtil;

import ca.docilecraft.color.PColor;
import ca.docilecraft.fetchers.NameFetcher;
import ca.docilecraft.fetchers.UUIDFetcher;

public class CustomsHandler{
	public enum PlayerOption{
		PREFIX, SUFFIX, COLOR;
	}
	
	private static String playerOptionToString(PlayerOption option){
		if(option == PlayerOption.PREFIX) return "prefix";
		if(option == PlayerOption.SUFFIX) return "suffix";
		if(option == PlayerOption.COLOR) return "color";
		return null;
	}
	
	private static FileConfiguration players = PrefixedConfig.getPlayers();
	
	protected static void reload(){
		players = PrefixedConfig.getPlayers();
	}
	
	/*
	 * PLAYER
	 */
	
	protected static UUID getUUIDStartsWith(String player){
		for(String key : players.getKeys(false)){
			String name = players.getConfigurationSection(key).getString("name");
			if(name != null && StringUtil.startsWithIgnoreCase(name, player) && key.matches(Prefixed.UUIDRegex)){
				return UUID.fromString(key);
			}
		}
		return null;
	}
	
	protected static UUID getUUID(String player){
		for(String key : players.getKeys(false)){
			String name = players.getConfigurationSection(key).getString("name");
			if(name != null && name.equalsIgnoreCase(player) && key.matches(Prefixed.UUIDRegex)){
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
	
	protected static String get(UUID uuid, PlayerOption option){
		ConfigurationSection section = getPlayer(uuid);
		return section == null ? null : section.getString(playerOptionToString(option));
	}
	
	protected static void set(UUID uuid, PlayerOption option, String value){
		getPlayer(uuid).set(playerOptionToString(option), value);
		PrefixedConfig.savePlayers();
	}
	
	protected static String get(String player, PlayerOption option){
		ConfigurationSection section = getPlayer(player);
		return section == null ? null : section.getString(playerOptionToString(option));
	}
	
	protected static void set(String player, PlayerOption option, String value){
		getPlayer(player).set(playerOptionToString(option), value);
		PrefixedConfig.savePlayers();
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
			if(section.contains("name") && section.getString("name").equalsIgnoreCase(name)){
				Prefixed.log(3, "Player found");
				return section;
			}
		}
		Prefixed.log(3, "Player not found");
		return null;
	}
	
	protected static boolean setPlayer(String name, String prefix, String suffix, String color){
		//Find UUID
		UUID uuid = null;
		try{
			uuid = UUIDFetcher.getUUIDOf(name);
			
			if(uuid == null)
				return false;
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
		
		if(sectionName.matches(Prefixed.UUIDRegex)){
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