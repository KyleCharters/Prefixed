package ca.docilecraft;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PrefixedConfig{
	private static Prefixed main;
	private static FileConfiguration config;
	
	protected static void reload(Prefixed prefixed){
		main = prefixed;
		config = prefixed.getConfig();
		
		reloadConfig();
		reloadPlayers();
	}
	
	/*
	 * PREFIXED CONFIG
	 */
	
	protected static String format;
	protected static boolean useMultiple;
	protected static boolean useDisplayName;
	protected static boolean useTabList;
	
	protected static void reloadConfig(){
		main.reloadConfig();
		format = config.getString("format");
		useMultiple = config.getBoolean("useMultiple");
		useDisplayName = config.getBoolean("useDisplayName");
		useTabList = config.getBoolean("useTabList");
	}
	
	protected static void saveConfig(){
		config.set("format", format);
		config.set("useMultiple", useMultiple);
		config.set("useDisplayName", useDisplayName);
		config.set("useTabList", useTabList);
		main.saveConfig();
	}
	
	/*
	 * PLAYERS CONFIG
	 */
	
	private static File playersFile = null;
	private static FileConfiguration players = null;
	
	protected static void reloadPlayers(){
		//Creating file if it is null
		if(playersFile == null){
			playersFile = new File(main.getDataFolder(), "players.yml");
			try{
				playersFile.createNewFile();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		players = YamlConfiguration.loadConfiguration(playersFile);
	}
	
	protected static FileConfiguration getPlayers(){
		if(players == null)
			reloadPlayers();
		return players;
	}
	
	protected static void savePlayers(){
		if(players == null || playersFile == null){
			return;
		}
		try{
			getPlayers().save(playersFile);
		}catch(IOException e){
			Prefixed.log(3, "Unable to save config to: " + playersFile);
		}
	}
}
