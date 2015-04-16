package ca.docilecraft;

import org.bukkit.entity.Player;

public class PlayerInfo{
	
	//Returns player's name [Display name or real]
	protected static String getName(Player player){
		return PrefixedConfig.useDisplayName ? player.getDisplayName() : player.getName();
	}
	
	//Returns player's name [Fit for tab list]
	public static String playerNameTag(Player player){
		String chatColor = getColor(player);
		String playerName = getName(player);
		
		if(chatColor.length() > 0){
			if((chatColor + playerName + "§f").length() > 16){
				return new StringBuilder().append(new StringBuilder().append(chatColor).append(playerName).toString().substring(0, 13)).append("-§f").toString();
			}else{
				return new StringBuilder().append(chatColor).append(playerName).append("§f").toString();
			}
		}
		return playerName;
	}
	
	//Returns player's prefix
	protected static String getPrefix(Player player){
		StringBuilder prefix = new StringBuilder();
		String customPrefix = CustomsHandler.getPrefix(player);
		
		if(customPrefix != null){
			prefix.append(customPrefix+PColor.WHITE);
			
			if(PrefixedConfig.useMultiple && HooksHandler.vaultEnabled){
				prefix.append(HooksHandler.vault.getPlayerPrefix(player)+PColor.WHITE);
				
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupPre = HooksHandler.vault.getGroupPrefix(player.getWorld(), group);
					if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(PColor.WHITE);
				}
			}
		}else if(HooksHandler.vaultEnabled){
			prefix.append(HooksHandler.vault.getPlayerPrefix(player)+PColor.WHITE);
			
			if(PrefixedConfig.useMultiple){
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupPre = HooksHandler.vault.getGroupPrefix(player.getWorld(), group);
					if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(PColor.WHITE);
				}
			}
		}
		
		return prefix.toString();
	}
	
	//Returns player's Suffix
	protected static String getSuffix(Player player){
		StringBuilder suffix = new StringBuilder();
		String customSuffix = CustomsHandler.getSuffix(player);
		
		if(customSuffix != null){
			suffix.append(customSuffix+PColor.WHITE);
			
			if(PrefixedConfig.useMultiple && HooksHandler.vaultEnabled){
				suffix.append(HooksHandler.vault.getPlayerSuffix(player)+PColor.WHITE);
				
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupSuf = HooksHandler.vault.getGroupSuffix(player.getWorld(), group);
					if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(PColor.WHITE);
				}
			}
		}else if(HooksHandler.vaultEnabled){
			suffix.append(HooksHandler.vault.getPlayerSuffix(player)+PColor.WHITE);
			
			if(PrefixedConfig.useMultiple){
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupSuf = HooksHandler.vault.getGroupSuffix(player.getWorld(), group);
					if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(PColor.WHITE);
				}
			}
		}
		
		return suffix.toString();
	}
	
	//Returns player's ChatColor
	protected static String getColor(Player player){
		String c = "";
		
		if(CustomsHandler.getColor(player) != null) c = PColor.getCodeFromString(CustomsHandler.getColor(player));
		else if(player.hasPermission("Prefixed.color.black"))c = PColor.BLACK;
		else if(player.hasPermission("Prefixed.color.darkblue"))c = PColor.DARKBLUE;
		else if(player.hasPermission("Prefixed.color.darkgreen"))c = PColor.DARKGREEN;
		else if(player.hasPermission("Prefixed.color.darkaqua"))c = PColor.DARKAQUA;
		else if(player.hasPermission("Prefixed.color.darkred"))c = PColor.DARKRED;
		else if(player.hasPermission("Prefixed.color.purple"))c = PColor.PURPLE;
		else if(player.hasPermission("Prefixed.color.gold"))c = PColor.GOLD;
		else if(player.hasPermission("Prefixed.color.gray"))c = PColor.GRAY;
		else if(player.hasPermission("Prefixed.color.darkgray"))c = PColor.DARKGRAY;
		else if(player.hasPermission("Prefixed.color.blue"))c = PColor.BLUE;
		else if(player.hasPermission("Prefixed.color.green"))c = PColor.GREEN;
		else if(player.hasPermission("Prefixed.color.aqua"))c = PColor.AQUA;
		else if(player.hasPermission("Prefixed.color.red"))c = PColor.RED;
		else if(player.hasPermission("Prefixed.color.pink"))c = PColor.PINK;
		else if(player.hasPermission("Prefixed.color.yellow"))c = PColor.YELLOW;
		
		if(player.hasPermission("Prefixed.color.obfuscated")){
			c += PColor.OBFUSCATED;
			return c;
		}
		if(player.hasPermission("Prefixed.color.bold"))c += PColor.BOLD;
		if(player.hasPermission("Prefixed.color.strike"))c += PColor.STRIKE;
		if(player.hasPermission("Prefixed.color.underline"))c += PColor.UNDERLINE;
		if(player.hasPermission("Prefixed.color.italic"))c += PColor.ITALIC;
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