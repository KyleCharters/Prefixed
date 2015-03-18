package ca.docilecraft;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class PrefixedConfig{
	
	private static Prefixed main;
	private static FileConfiguration config;
	
	protected static void loadConfig(Prefixed prefixed){
		main = prefixed;
		config = prefixed.getConfig();
		
		format = config.getString("format");
		useMultiple = config.getBoolean("useMultiple");
		useDisplayName = config.getBoolean("useDisplayName");
		useTabList = config.getBoolean("useTabList");
		players = config.getConfigurationSection("players");
		groups = config.getConfigurationSection("groups");
	}
	
	protected static void saveConfig(){
		config.set("format", format);
		config.set("useMultiple", useMultiple);
		config.set("useDisplayName", useDisplayName);
		config.set("useTabList", useTabList);
		config.set("players", players);
		config.set("groups", groups);
		main.saveConfig();
	}
	
	protected static String format;
	protected static boolean useMultiple;
	protected static boolean useDisplayName;
	protected static boolean useTabList;
	protected static ConfigurationSection players;
	protected static ConfigurationSection groups;
}