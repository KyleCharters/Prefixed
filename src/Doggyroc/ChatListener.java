package Doggyroc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onPlayerChat(final AsyncPlayerChatEvent e){
		if(e.isCancelled()) return;
		
//Strings&String Checks
		
		String world = e.getPlayer().getWorld().getName();
		String message = e.getMessage().replaceAll("%", "%%");
		Player player = e.getPlayer();
		String sender = e.getPlayer().getDisplayName();
		String prefix = PermissionsEx.getPermissionManager().getUser(player).getPrefix(world).replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2");
		String suffix = PermissionsEx.getPermissionManager().getUser(player).getSuffix(world).replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2");
		
//if Statements
		
		if(prefix == null) prefix = "";
		if(suffix == null) suffix = "";
		if(PermissionsEx.getPermissionManager().getUser(e.getPlayer()).has("Prefixed.colour")){
			message = e.getMessage().replaceAll("%", "%%").replaceAll("(&([a-fl-o0-9A-FL-O]))", "\247$2");
		}
			
//Actual Reformatting
		
		e.setFormat(prefix + ChatColor.WHITE + sender + suffix + ChatColor.WHITE + ": " + message);
	}
		
}
