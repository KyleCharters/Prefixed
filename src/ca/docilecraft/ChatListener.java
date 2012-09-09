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
	public void onPlayerChat(final AsyncPlayerChatEvent e){
		if(e.isCancelled()) return;
		
		String message;
		String world = e.getPlayer().getWorld().getName();
		String player;

//If Statements
		
		if(perm(e.getPlayer(), "Prefixed.colour"))message = e.getMessage().replace("%", "%%").replace("&", "§");	
		else message = e.getMessage().replace("%", "%%");
		
		if(main.getConfig().getBoolean("useDisplayName")) player = e.getPlayer().getDisplayName();
		else player = e.getPlayer().getName();
		
//Formatting
		
		e.setFormat(main.getConfig().getString("format").replace("-Prefix", prefix(e.getPlayer())).replace("-Suffix", suffix(e.getPlayer())).replace("-Player", player).replace("-World", world).replace("&", "§").replace("-Message", message));
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
