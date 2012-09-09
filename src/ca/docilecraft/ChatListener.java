package ca.docilecraft;

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
		
		e.setFormat(main.getConfig().getString("format").replace("-Prefix", prefix(e.getPlayer().getName(), world)).replace("-Suffix", suffix(e.getPlayer().getName(), world)).replace("-Player", player).replace("-World", e.getPlayer().getWorld().getName()).replace("&", "§").replace("-Message", message));
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
	
	public synchronized boolean perm(Player player, String perm){
		if(player.hasPermission(perm)) return true;
		else return false;
	}

}
