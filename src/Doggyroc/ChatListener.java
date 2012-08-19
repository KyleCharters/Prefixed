package Doggyroc;

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
		String prefix = PermissionsEx.getPermissionManager().getUser(e.getPlayer()).getPrefix(world).replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2");
		String suffix = PermissionsEx.getPermissionManager().getUser(e.getPlayer()).getSuffix(world).replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2");
		
//If Statements
		
		if(prefix == null) prefix = "";
		if(suffix == null) suffix = "";
		if(PermissionsEx.getPermissionManager().getUser(e.getPlayer()).has("Prefixed.colour")){
			message = e.getMessage().replaceAll("%", "%%").replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2");
		}else {
			message = e.getMessage().replaceAll("%", "%%");
		}
		
//Formatting
		
		e.setFormat(plugin.getConfig().getString("format").replaceAll("-Prefix", prefix).replaceAll("-Suffix", suffix).replaceAll("-Player", player).replaceAll("-Message", message).replaceAll("%", "%%").replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2"));
	}
}
