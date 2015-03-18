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
		String colours = getColours(player);
		String playerName = getName(player);
		
		if(colours.length() > 0){
			if((colours + playerName + "§f").length() > 16){
				return new StringBuilder().append(new StringBuilder().append(colours).append(playerName).toString().substring(0, 13)).append("-§f").toString();
			}else{
				return new StringBuilder().append(colours).append(playerName).append("§f").toString();
			}
		}
		return playerName;
	}
	
	//Returns player's prefix
	protected static String getPrefix(Player player){
		StringBuilder prefix = new StringBuilder().append(Prefixed.chat.getPlayerPrefix(player)).append(ChatColor.WHITE);
		
		if(PrefixedConfig.useMultiple){
			for(String group : Prefixed.chat.getPlayerGroups(player)){
				String groupPre = Prefixed.chat.getGroupPrefix(player.getWorld(), group);
				if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(ChatColor.WHITE);
			}
		}
		
		prefix.append(CustomsHandler.getPrefix(player));
		
		return prefix.toString();
	}
	
	//Returns player's Suffix
	protected static String getSuffix(Player player){
		StringBuilder suffix = new StringBuilder().append(Prefixed.chat.getPlayerSuffix(player)).append(ChatColor.WHITE);
		
		if(PrefixedConfig.useMultiple){
			for(String group : Prefixed.chat.getPlayerGroups(player)){
				String groupSuf = Prefixed.chat.getGroupSuffix(player.getWorld(), group);
				if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(ChatColor.WHITE);
			}
		}
		
		suffix.append(CustomsHandler.getSuffix(player));
		
		return suffix.toString();
	}
	
	//Returns player's colours
	protected static String getColours(Player player){
		String c = "";
		if(player.hasPermission("Prefixed.colour.black"))c = Colours.black;
		else if(player.hasPermission("Prefixed.colour.dblue"))c = Colours.darkblue;
		else if(player.hasPermission("Prefixed.colour.dgreen"))c = Colours.darkgreen;
		else if(player.hasPermission("Prefixed.colour.daqua"))c = Colours.darkaqua;
		else if(player.hasPermission("Prefixed.colour.dred"))c = Colours.darkred;
		else if(player.hasPermission("Prefixed.colour.purple"))c = Colours.purple;
		else if(player.hasPermission("Prefixed.colour.gold"))c = Colours.gold;
		else if(player.hasPermission("Prefixed.colour.grey"))c = Colours.grey;
		else if(player.hasPermission("Prefixed.colour.dgrey"))c = Colours.darkgrey;
		else if(player.hasPermission("Prefixed.colour.blue"))c = Colours.blue;
		else if(player.hasPermission("Prefixed.colour.green"))c = Colours.green;
		else if(player.hasPermission("Prefixed.colour.aqua"))c = Colours.aqua;
		else if(player.hasPermission("Prefixed.colour.red"))c = Colours.red;
		else if(player.hasPermission("Prefixed.colour.pink"))c = Colours.pink;
		else if(player.hasPermission("Prefixed.colour.yellow"))c = Colours.yellow;
		if(player.hasPermission("Prefixed.colour.obfuscated")){
			c = c + Colours.obfuscated;
			return c;
		}
		if(player.hasPermission("Prefixed.colour.bold"))c = c + Colours.bold;
		if(player.hasPermission("Prefixed.colour.strike"))c = c+ Colours.strike;
		if(player.hasPermission("Prefixed.colour.underline"))c = c + Colours.underline;
		if(player.hasPermission("Prefixed.colour.italic"))c = c + Colours.italic;
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