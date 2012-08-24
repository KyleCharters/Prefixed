package ca.docilecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatListener implements Listener {
	
	Prefixed plugin;
	
	public ChatListener(Prefixed instance){
		plugin = instance;
	}
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent e){
		if(e.isCancelled()) return;
		
		String message;
		String world = e.getPlayer().getWorld().getName();
		String player = e.getPlayer().getDisplayName();
		String prefix = PermissionsEx.getPermissionManager().getUser(e.getPlayer()).getPrefix(world);
		String suffix = PermissionsEx.getPermissionManager().getUser(e.getPlayer()).getSuffix(world);
		
//If Statements
		
		if(prefix == null) prefix = "";
		if(suffix == null) suffix = "";
		if(PermissionsEx.getPermissionManager().getUser(e.getPlayer()).has("Prefixed.colour")){
			message = e.getMessage().replaceAll("%", "%%").replace("&", "§");
		}else {
			message = e.getMessage().replace("%", "%%");
		}
		
//Formatting
		
		e.setFormat(plugin.getConfig().getString("format").replace("-Prefix", prefix).replace("-Suffix", suffix).replace("-Player", player).replace("&", "§").replace("-Message", message));
	}
}
