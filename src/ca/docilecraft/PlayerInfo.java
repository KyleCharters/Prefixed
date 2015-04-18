package ca.docilecraft;

import static ca.docilecraft.color.PColor.*;

import org.bukkit.entity.Player;

import ca.docilecraft.color.PColor;
import ca.docilecraft.color.PColorBuilder;

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
			prefix.append(customPrefix+WHITE);
			
			if(PrefixedConfig.useMultiple && HooksHandler.vaultEnabled){
				prefix.append(HooksHandler.vault.getPlayerPrefix(player)+WHITE);
				
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupPre = HooksHandler.vault.getGroupPrefix(player.getWorld(), group);
					if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(WHITE);
				}
			}
		}else if(HooksHandler.vaultEnabled){
			prefix.append(HooksHandler.vault.getPlayerPrefix(player)+WHITE);
			
			if(PrefixedConfig.useMultiple){
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupPre = HooksHandler.vault.getGroupPrefix(player.getWorld(), group);
					if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(WHITE);
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
			suffix.append(customSuffix+WHITE);
			
			if(PrefixedConfig.useMultiple && HooksHandler.vaultEnabled){
				suffix.append(HooksHandler.vault.getPlayerSuffix(player)+WHITE);
				
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupSuf = HooksHandler.vault.getGroupSuffix(player.getWorld(), group);
					if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(WHITE);
				}
			}
		}else if(HooksHandler.vaultEnabled){
			suffix.append(HooksHandler.vault.getPlayerSuffix(player)+WHITE);
			
			if(PrefixedConfig.useMultiple){
				for(String group : HooksHandler.vault.getPlayerGroups(player)){
					String groupSuf = HooksHandler.vault.getGroupSuffix(player.getWorld(), group);
					if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(WHITE);
				}
			}
		}
		
		return suffix.toString();
	}
	
	//Returns player's Color
	protected static String getColor(Player player){
		PColorBuilder color = new PColorBuilder();
		
		if(CustomsHandler.getColor(player) != null) color = PColor.getCodesFromString(CustomsHandler.getColor(player));
		else if(player.hasPermission("Prefixed.color.black"))color.set(BLACK);
		else if(player.hasPermission("Prefixed.color.darkblue"))color.set(DARKBLUE);
		else if(player.hasPermission("Prefixed.color.darkgreen"))color.set(DARKGREEN);
		else if(player.hasPermission("Prefixed.color.darkaqua"))color.set(DARKAQUA);
		else if(player.hasPermission("Prefixed.color.darkred"))color.set(DARKRED);
		else if(player.hasPermission("Prefixed.color.purple"))color.set(PURPLE);
		else if(player.hasPermission("Prefixed.color.gold"))color.set(GOLD);
		else if(player.hasPermission("Prefixed.color.gray"))color.set(GRAY);
		else if(player.hasPermission("Prefixed.color.darkgray"))color.set(DARKGRAY);
		else if(player.hasPermission("Prefixed.color.blue"))color.set(BLUE);
		else if(player.hasPermission("Prefixed.color.green"))color.set(GREEN);
		else if(player.hasPermission("Prefixed.color.aqua"))color.set(AQUA);
		else if(player.hasPermission("Prefixed.color.red"))color.set(RED);
		else if(player.hasPermission("Prefixed.color.pink"))color.set(PINK);
		else if(player.hasPermission("Prefixed.color.yellow"))color.set(YELLOW);
		if(player.hasPermission("Prefixed.color.obfuscated")){
			color.append(OBFUSCATED);
			return color.toString();
		}
		if(player.hasPermission("Prefixed.color.bold"))color.append(BOLD);
		if(player.hasPermission("Prefixed.color.strike"))color.append(STRIKE);
		if(player.hasPermission("Prefixed.color.underline"))color.append(UNDERLINE);;
		if(player.hasPermission("Prefixed.color.italic"))color.append(ITALIC);;
		return color.toString();
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