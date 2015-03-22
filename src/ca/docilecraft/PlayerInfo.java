package ca.docilecraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerInfo{
	
	//Returns player's name [Display name or real]
	protected static String getName(Player player){
		return PrefixedConfig.useDisplayName ? player.getDisplayName() : player.getName();
	}
	
	//Returns player's name [Fit for tab list]
	protected static String playerNameTag(Player player){
		String ChatColor = getChatColor(player);
		String playerName = getName(player);
		
		if(ChatColor.length() > 0){
			if((ChatColor + playerName + "§f").length() > 16){
				return new StringBuilder().append(new StringBuilder().append(ChatColor).append(playerName).toString().substring(0, 13)).append("-§f").toString();
			}else{
				return new StringBuilder().append(ChatColor).append(playerName).append("§f").toString();
			}
		}
		return playerName;
	}
	
	//Returns player's prefix
	protected static String getPrefix(Player player){
		StringBuilder prefix = new StringBuilder().append(CustomsHandler.getPrefix(player.getName())+ChatColor.WHITE).append(ChatColor.WHITE);
		
		if(VaultManager.enabled){
			prefix.append(VaultManager.chat.getPlayerPrefix(player)+ChatColor.WHITE);
			
			if(PrefixedConfig.useMultiple){
				for(String group : VaultManager.chat.getPlayerGroups(player)){
					String groupPre = VaultManager.chat.getGroupPrefix(player.getWorld(), group);
					if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(ChatColor.WHITE);
				}
			}
		}
		
		return prefix.toString();
	}
	
	//Returns player's Suffix
	protected static String getSuffix(Player player){
		StringBuilder suffix = new StringBuilder().append(CustomsHandler.getSuffix(player.getName())+ChatColor.WHITE).append(ChatColor.WHITE);
		
		if(VaultManager.enabled){
			suffix.append(VaultManager.chat.getPlayerSuffix(player)+ChatColor.WHITE);
			
			if(PrefixedConfig.useMultiple){
				for(String group : VaultManager.chat.getPlayerGroups(player)){
					String groupSuf = VaultManager.chat.getGroupSuffix(player.getWorld(), group);
					if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(ChatColor.WHITE);
				}
			}
		}
		
		return suffix.toString();
	}
	
	//Returns player's ChatColor
	protected static String getChatColor(Player player){
		String c = "";
		if(player.hasPermission("Prefixed.ChatColor.black"))c = ChatColor.BLACK.toString();
		else if(player.hasPermission("Prefixed.ChatColor.dblue"))c = ChatColor.DARK_BLUE.toString();
		else if(player.hasPermission("Prefixed.ChatColor.dgreen"))c = ChatColor.DARK_GREEN.toString();
		else if(player.hasPermission("Prefixed.ChatColor.daqua"))c = ChatColor.DARK_AQUA.toString();
		else if(player.hasPermission("Prefixed.ChatColor.dred"))c = ChatColor.DARK_RED.toString();
		else if(player.hasPermission("Prefixed.ChatColor.purple"))c = ChatColor.DARK_PURPLE.toString();
		else if(player.hasPermission("Prefixed.ChatColor.gold"))c = ChatColor.GOLD.toString();
		else if(player.hasPermission("Prefixed.ChatColor.grey"))c = ChatColor.GRAY.toString();
		else if(player.hasPermission("Prefixed.ChatColor.dgrey"))c = ChatColor.DARK_GRAY.toString();
		else if(player.hasPermission("Prefixed.ChatColor.blue"))c = ChatColor.BLUE.toString();
		else if(player.hasPermission("Prefixed.ChatColor.green"))c = ChatColor.GREEN.toString();
		else if(player.hasPermission("Prefixed.ChatColor.aqua"))c = ChatColor.AQUA.toString();
		else if(player.hasPermission("Prefixed.ChatColor.red"))c = ChatColor.RED.toString();
		else if(player.hasPermission("Prefixed.ChatColor.pink"))c = ChatColor.LIGHT_PURPLE.toString();
		else if(player.hasPermission("Prefixed.ChatColor.yellow"))c = ChatColor.YELLOW.toString();
		if(player.hasPermission("Prefixed.ChatColor.obfuscated")){
			c = c + ChatColor.MAGIC.toString();
			return c;
		}
		if(player.hasPermission("Prefixed.ChatColor.bold"))c = c + ChatColor.BOLD.toString();
		if(player.hasPermission("Prefixed.ChatColor.strike"))c = c+ ChatColor.STRIKETHROUGH.toString();
		if(player.hasPermission("Prefixed.ChatColor.underline"))c = c + ChatColor.UNDERLINE.toString();
		if(player.hasPermission("Prefixed.ChatColor.italic"))c = c + ChatColor.ITALIC.toString();
		return c;
	}
	
	//Returns player's current world name
	protected static String getWorld(Player player){
		return player.getWorld().getName();
	}
	
	//Returns player's IP address
	protected static String getAddress(Player player){
		return player.getAddress().getHostString();
	}
	
	//Returns player's IP port
	protected static String getPort(Player player){
		return String.valueOf(player.getAddress().getPort());
	}
}