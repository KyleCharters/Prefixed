package ca.docilecraft;

import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import ca.docilecraft.fetchers.NameFetcher;
import ca.docilecraft.fetchers.UUIDFetcher;

public class CustomsHandler{
	
	/*
	 * PREFIX
	 */
	
	protected static String getPrefix(String player){
		String prefix = getPlayer(player).getString("prefix");
		return prefix == null ? "" : prefix;
	}
	
	protected static void setPrefix(String player, String prefix){
		getPlayer(player).set("prefix", prefix);
		PrefixedConfig.savePlayers();
	}
	
	/*
	 * SUFFIX
	 */
	
	protected static String getSuffix(String player){
		String suffix = getPlayer(player).getString("suffix");
		return suffix == null ? "" : suffix;
	}
	
	protected static void setSuffix(String player, String suffix){
		getPlayer(player).set("suffix", suffix);
		PrefixedConfig.savePlayers();
	}
	
	/*
	 * UTILITY
	 */
	
	private static ConfigurationSection getPlayer(String player){
		return PrefixedConfig.getPlayers().getConfigurationSection(player);
	}
	
	protected static void addPlayer(String name, String prefix, String suffix){
		ConfigurationSection section = PrefixedConfig.getPlayers().createSection(name);
		
		if(prefix != null){
			section.set("prefix", prefix);
		}
		if(suffix != null){
			section.set("suffix", suffix);
		}
		
		try{
			section.set("uuid", UUIDFetcher.getUUIDOf(name));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		PrefixedConfig.savePlayers();
	}
	
	protected static void removePlayer(String name){
		PrefixedConfig.getPlayers().set(name, null);
		PrefixedConfig.savePlayers();
	}
	
	protected static void checkPlayers(){
		try{
			if(PrefixedConfig.getPlayers().getKeys(false).isEmpty()){
				Prefixed.log(0, "Players file is empty");
				return;
			}
			
			Prefixed.log(0, "Checking player uuids");
			
			for(String p : PrefixedConfig.getPlayers().getKeys(false)){
				ConfigurationSection section = PrefixedConfig.getPlayers().getConfigurationSection(p);
				
				//Section does not contain uuid or invalid uuid
				if(!section.contains("uuid") || !section.getString("uuid").matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")){
					UUID playerUUID = UUIDFetcher.getUUIDOf(p);
					if(playerUUID == null){
						Prefixed.log(1, "User "+p+" does not exist");
						continue;
					}
					section.set("uuid", playerUUID.toString());
					Prefixed.log(0, "UUID set for player "+p);
				//Section contains valid uuid
				}else{
					String playerName = NameFetcher.getUsernameOf(UUID.fromString(section.getString("uuid")));
					
					//player name does not match uuid's player
					if(!p.equals(playerName) && playerName != null){
						//Duplicate sections
						ConfigurationSection oldSection = section;
						section = PrefixedConfig.getPlayers().createSection(playerName);
						
						for(String key : oldSection.getKeys(false)){
							section.set(key, oldSection.get(key));
						}
						
						//Set uuid 
						section.set("uuid", UUIDFetcher.getUUIDOf(playerName).toString());
						
						//Delete old section
						PrefixedConfig.getPlayers().set(p, null);
						
						Prefixed.log(0, "Player "+p+" changed their name to: "+playerName);
					}
				}
			}
			PrefixedConfig.savePlayers();
			
			Prefixed.log(0, "Finished checking player uuids");
		}catch(Exception e){
			e.printStackTrace();
			Prefixed.log(3, "Error checking player uuids");
		}
	}
}
