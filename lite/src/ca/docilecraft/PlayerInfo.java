package ca.docilecraft;

import org.bukkit.entity.Player;

public class PlayerInfo{
	
	//Returns player's name [Display name or real]
	protected static String getName(Player player){
		return PrefixedLite.useDisplayName ? player.getDisplayName() : player.getName();
	}
	
	//Returns player's name [Fit for tab list]
	public static String playerNameTag(Player player){
		String color = getColor(player);
		String playerName = getName(player);
		
		if(color.length() > 0){
			if((color + playerName + "§f").length() > 16){
				return new StringBuilder().append(new StringBuilder().append(color).append(playerName).toString().substring(0, 13)).append("-§f").toString();
			}else{
				return new StringBuilder().append(color).append(playerName).append("§f").toString();
			}
		}
		return playerName;
	}
	
	//Returns player's prefix
	protected static String getPrefix(Player player){
		StringBuilder prefix = new StringBuilder();
		
		prefix.append(PrefixedLite.vault.getPlayerPrefix(player)+PColor.WHITE);
		
		if(PrefixedLite.useMultiple){
			for(String group : PrefixedLite.vault.getPlayerGroups(player)){
				String groupPre = PrefixedLite.vault.getGroupPrefix(player.getWorld(), group);
				if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(PColor.WHITE);
			}
		}
		
		return prefix.toString();
	}
	
	//Returns player's Suffix
	protected static String getSuffix(Player player){
		StringBuilder suffix = new StringBuilder();
		
		suffix.append(PrefixedLite.vault.getPlayerSuffix(player)+PColor.WHITE);
		
		if(PrefixedLite.useMultiple){
			for(String group : PrefixedLite.vault.getPlayerGroups(player)){
				String groupSuf = PrefixedLite.vault.getGroupSuffix(player.getWorld(), group);
				if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(PColor.WHITE);
			}
		}
		
		return suffix.toString();
	}
	
	//Returns player's ChatColor
	protected static String getColor(Player player){
		String c = "";
		
		if(player.hasPermission("PrefixedLite.color.black"))c = PColor.BLACK;
		else if(player.hasPermission("PrefixedLite.color.darkblue"))c = PColor.DARKBLUE;
		else if(player.hasPermission("PrefixedLite.color.darkgreen"))c = PColor.DARKGREEN;
		else if(player.hasPermission("PrefixedLite.color.darkaqua"))c = PColor.DARKAQUA;
		else if(player.hasPermission("PrefixedLite.color.darkred"))c = PColor.DARKRED;
		else if(player.hasPermission("PrefixedLite.color.purple"))c = PColor.PURPLE;
		else if(player.hasPermission("PrefixedLite.color.gold"))c = PColor.GOLD;
		else if(player.hasPermission("PrefixedLite.color.gray"))c = PColor.GRAY;
		else if(player.hasPermission("PrefixedLite.color.darkgray"))c = PColor.DARKGRAY;
		else if(player.hasPermission("PrefixedLite.color.blue"))c = PColor.BLUE;
		else if(player.hasPermission("PrefixedLite.color.green"))c = PColor.GREEN;
		else if(player.hasPermission("PrefixedLite.color.aqua"))c = PColor.AQUA;
		else if(player.hasPermission("PrefixedLite.color.red"))c = PColor.RED;
		else if(player.hasPermission("PrefixedLite.color.pink"))c = PColor.PINK;
		else if(player.hasPermission("PrefixedLite.color.yellow"))c = PColor.YELLOW;
		if(player.hasPermission("PrefixedLite.color.obfuscated")){
			c += PColor.OBFUSCATED;
			return c;
		}
		if(player.hasPermission("PrefixedLite.color.bold"))c += PColor.BOLD;
		if(player.hasPermission("PrefixedLite.color.strike"))c += PColor.STRIKE;
		if(player.hasPermission("PrefixedLite.color.underline"))c += PColor.UNDERLINE;
		if(player.hasPermission("PrefixedLite.color.italic"))c += PColor.ITALIC;
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