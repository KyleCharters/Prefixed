package ca.docilecraft;

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
		String player = e.getPlayer().getDisplayName();

//If Statements
		
		if(e.getPlayer().hasPermission("Prefixed.colour")){
			message = e.getMessage().replace("%", "%%").replace("&", "§");
		}else{
			message = e.getMessage().replace("%", "%%");
		}
		
//Formatting
		
		e.setFormat(main.getConfig().getString("format").replace("-Prefix", prefix(e.getPlayer().getName(), world)).replace("-Suffix", suffix(e.getPlayer().getName(), world)).replace("-Player", player).replace("&", "§").replace("-Message", message));
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
	
	
	
	
	public String prefix(String player, String world){
		if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			String prefix = Prefixed.chat.getPlayerPrefix(world, player);
			if(prefix == null) prefix = "";
			return prefix;
		}
		return "";
	}

	public String suffix(String player, String world){
		if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			String suffix = Prefixed.chat.getPlayerSuffix(world, player);
			if(suffix == null) suffix = "";
			return suffix;
		}
		return "";
	}

}
