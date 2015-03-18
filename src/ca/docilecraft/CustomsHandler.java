package ca.docilecraft;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CustomsHandler{
	/*
	 * PREFIX
	 */
	
	public static String getPrefix(Player player){
		return getPlayer(player).getString("prefix");
	}
	
	public static void setPrefix(Player player, String prefix){
		setPrefix(player.getName(), prefix);
	}
	
	public static void setPrefix(String player, String prefix){
		getPlayer(player).set("prefix", prefix);
		PrefixedConfig.saveConfig();
	}
	
	/*
	 * SUFFIX
	 */
	
	public static String getSuffix(Player player){
		return getPlayer(player).getString("suffix");
	}
	
	public static void setSuffix(Player player, String suffix){
		setSuffix(player.getName(), suffix);
	}
	
	public static void setSuffix(String player, String suffix){
		getPlayer(player).set("suffix", suffix);
		PrefixedConfig.saveConfig();
	}
	
	/*
	 * TAB COLOUR
	 */
	
	public static String getColour(Player player){
		return getPlayer(player).getString("color");
	}
	
	public static void setColour(Player player, String color){
		
	}
	
	
	private static ConfigurationSection getPlayer(String player){
		return PrefixedConfig.players.getConfigurationSection(player);
	}
	
	private static ConfigurationSection getPlayer(Player player){
		return getPlayer(player.getName());
	}
	
	@SuppressWarnings ("unused")
	private static ConfigurationSection getGroup(String group){
		return PrefixedConfig.groups.getConfigurationSection(group);
	}
}
