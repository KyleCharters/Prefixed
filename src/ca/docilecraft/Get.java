package ca.docilecraft;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

class Get{
	
	private static FileConfiguration config = Prefixed.config;
	
	/* 
	 * Chat Methods
	 * -------------
	 */
	
	public static String chatPrefix(Player player){
		Chat chat = Prefixed.chat;
		
		StringBuilder prefix = new StringBuilder().append(chat.getPlayerPrefix(player)).append(ChatColor.WHITE);
		
		if(config.getBoolean("useMultiple")){
			//if multiple suffixes are enabled
			for(String group : chat.getPlayerGroups(player)){
				String groupPre = chat.getGroupPrefix(player.getWorld(), group);
				if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(ChatColor.WHITE);
			}
		}
		
		return prefix.toString();
	}
	
	public static String chatSuffix(Player player){
		Chat chat = Prefixed.chat;
		
		StringBuilder suffix = new StringBuilder().append(chat.getPlayerSuffix(player)).append(ChatColor.WHITE);
		
		if(config.getBoolean("useMultiple")){
			//if multiple suffixes are enabled
			for(String group : chat.getPlayerGroups(player)){
				String groupSuf = chat.getGroupSuffix(player.getWorld(), group);
				if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(ChatColor.WHITE);
			}
		}
		
		return suffix.toString();
	}
	
	public static String chatFormat(Player player, String message){
		return ChatColor.translateAlternateColorCodes('&', config.getString("format")
			.replace("-Prefix", chatPrefix(player)) //Adding in suffix
			.replace("-Suffix", chatSuffix(player)) //Adding in suffix
			.replace("-Player", config.getBoolean("useDisplayName") ? player.getDisplayName() : player.getName()) //Adding in name [Check if using display name]
			.replace("-World", player.getWorld().getName())) //Adding with current world : End the colour formatting
			.replace("-IPAddress", String.valueOf(player.getAddress().getHostString()))
			.replace("-IPPort", String.valueOf(player.getAddress().getPort()))
			.replace("-Message", player.hasPermission("Prefixed.Colour")? ChatColor.translateAlternateColorCodes('&', message) : message) //Adding in the message [Check if player has permission to chat in colour]
			.replace("%", "%%");
	}
	
	/* 
	 * ------------
	 */
	
	/*
	 * Tab List
	 * ---------------------
	 */
	
	public static String playerColours(Player player){
		String c = "";
		if(player.hasPermission("Prefixed.colour.yellow"))c = "§e";
		else if(player.hasPermission("Prefixed.colour.pink"))c = "§d";
		else if(player.hasPermission("Prefixed.colour.red"))c = "§c";
		else if(player.hasPermission("Prefixed.colour.aqua"))c = "§b";
		else if(player.hasPermission("Prefixed.colour.bgreen"))c = "§a";
		else if(player.hasPermission("Prefixed.colour.indigo"))c = "§9";
		else if(player.hasPermission("Prefixed.colour.dgrey"))c = "§8";
		else if(player.hasPermission("Prefixed.colour.grey"))c = "§7";
		else if(player.hasPermission("Prefixed.colour.gold"))c = "§6";
		else if(player.hasPermission("Prefixed.colour.purple"))c = "§5";
		else if(player.hasPermission("Prefixed.colour.dred"))c = "§4";
		else if(player.hasPermission("Prefixed.colour.daqua"))c = "§3";
		else if(player.hasPermission("Prefixed.colour.dgreen"))c = "§2";
		else if(player.hasPermission("Prefixed.colour.dblue"))c = "§1";
		else if(player.hasPermission("Prefixed.colour.black"))c = "§0";
		if(player.hasPermission("Prefixed.colour.random")){
			c = c + "§k";
			return c;
		}
		if(player.hasPermission("Prefixed.colour.bold"))c = c + "§l";
		if(player.hasPermission("Prefixed.colour.strike"))c = c+ "§n";
		if(player.hasPermission("Prefixed.colour.underline"))c = c + "§m";
		if(player.hasPermission("Prefixed.colour.italic"))c = c + "§0";
		return c;
	}
	
	public static String playerNameTag(Player player){
		String colours = playerColours(player);
		String playerName = config.getBoolean("useDisplayName") ? player.getDisplayName() : player.getName();
		
		if(colours.length() > 0){
			if((colours + playerName + "§f").length() > 16){
				return new StringBuilder().append(new StringBuilder().append(colours).append(playerName).toString().substring(0, 13)).append("-§f").toString();
			}else{
				return new StringBuilder().append(colours).append(playerName).append("§f").toString();
			}
		}
		return playerName;
	}
	
	/*
	 * ---------------------
	 */
	
}
