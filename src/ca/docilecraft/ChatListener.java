package ca.docilecraft;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;



public class ChatListener implements Listener {
	
	Prefixed main;
	public ChatListener(Prefixed instance){
		main = instance;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent Chat){
		if(Chat.isCancelled()) return;
		
		String message;
		String world = Chat.getPlayer().getWorld().getName();
		String player;

//If Statements
		
		if(perm(Chat.getPlayer(), "Prefixed.colour"))message = Chat.getMessage().replace("%", "%%").replace("&", "§");	
		else message = Chat.getMessage().replace("%", "%%");
		
		if(main.getConfig().getBoolean("useDisplayName")) player = Chat.getPlayer().getDisplayName();
		else player = Chat.getPlayer().getName();
		
//Colour
		
		String Worldprefixcolour = worldTagColour();
		String ChatColour = chatColour();
		
//Formatting

		Chat.setFormat(main.getConfig().getString("format").replace("-Prefix", prefix(Chat.getPlayer())).replace("-Suffix", suffix(Chat.getPlayer())).replace("-Player", player).replace("-World", Worldprefixcolour + world).replace("&", "§").replace("-Message", ChatColour + message));
	}
	
	
	
	
/*
##     ## ######## ######## ##     ##  #######  ########   ######  
###   ### ##          ##    ##     ## ##     ## ##     ## ##    ## 
#### #### ##          ##    ##     ## ##     ## ##     ## ##       
## ### ## ######      ##    ######### ##     ## ##     ##  ######  
##     ## ##          ##    ##     ## ##     ## ##     ##       ## 
##     ## ##          ##    ##     ## ##     ## ##     ## ##    ## 
##     ## ########    ##    ##     ##  #######  ########   ######  
 */
	public String worldTagColour(){
		String ret = "";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("Yellow"))ret = "§e";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("PINK"))ret = "§d";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("RED"))ret = "§c";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("AQUA"))ret = "§b";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("BGREEN"))ret = "§a";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("INDIGO"))ret = "§9";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("DGREY"))ret = "§8";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("GREY"))ret = "§7";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("GOLD"))ret = "§6";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("PURPLE"))ret = "§5";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("DRED"))ret = "§4";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("DAQUA"))ret = "§3";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("DGREEN"))ret = "§2";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("DBLUE"))ret = "§1";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("BLACK"))ret = "§0";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("RANDOM")){
			ret = ret + "§k";
			return ret;
		}
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("BOLD"))ret = ret + "§l";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("STRIKE"))ret = ret+ "§n";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("UNDERLINE"))ret = ret + "§m";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("ITALIC"))ret = ret + "§0";
		return ret;
	}
	public String chatColour(){
		String ret = "";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("Yellow"))ret = "§e";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("PINK"))ret = "§d";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("RED"))ret = "§c";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("AQUA"))ret = "§b";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("BGREEN"))ret = "§a";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("INDIGO"))ret = "§9";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("DGREY"))ret = "§8";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("GREY"))ret = "§7";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("GOLD"))ret = "§6";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("PURPLE"))ret = "§5";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("DRED"))ret = "§4";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("DAQUA"))ret = "§3";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("DGREEN"))ret = "§2";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("DBLUE"))ret = "§1";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("BLACK"))ret = "§0";
		if(main.getConfig().getString("chatColour").equalsIgnoreCase("RANDOM")){
			ret = ret + "§k";
			return ret;
		}
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("BOLD"))ret = ret + "§l";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("STRIKE"))ret = ret+ "§n";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("UNDERLINE"))ret = ret + "§m";
		if(main.getConfig().getString("worldTagColour").equalsIgnoreCase("ITALIC"))ret = ret + "§0";
		return ret;
	}
	
	public String prefix(Player player){
		if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			Chat chat = Prefixed.chat;
			String p = player.getName();
			String prefix = chat.getPlayerPrefix(player.getWorld(), p)+"§f";
			if(!main.getConfig().getBoolean("useMultiple")){
				//if multiple prefixes aren't enabled
				String onlyPrefix = chat.getPlayerPrefix(player.getWorld(), p);
				if(onlyPrefix == null) return "";
				return onlyPrefix+"§f";
			}
			//Getting all prefixes
			for(String group : chat.getPlayerGroups(player)){
				String groupPre = chat.getGroupPrefix(player.getWorld(), group);
				if(!prefix.contains(groupPre+"§f")) prefix += groupPre+"§f";
			}
			return prefix;
		}
		return "";
	}

	public String suffix(Player player){
		if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			Chat chat = Prefixed.chat;
			String p = player.getName();
			String suffix = chat.getPlayerSuffix(player.getWorld(), p)+"§f";
			if(!main.getConfig().getBoolean("useMultiple")){
				//if multiple suffixes aren't enabled
				String onlySuffix = chat.getPlayerSuffix(player.getWorld(), p);
				if(onlySuffix == null) return "";
				return onlySuffix+"§f";
			}
			//Getting all suffixes
			for(String group : chat.getPlayerGroups(player)){
				String groupSuf = chat.getGroupSuffix(player.getWorld(), group);
				if(!suffix.contains(groupSuf+"§f")) suffix += groupSuf+"§f";
			}
			return suffix;
		}
		return "";
	}
	
	public synchronized boolean perm(Player player, String perm){
		if(player.hasPermission(perm)) return true;
		else return false;
	}
}
