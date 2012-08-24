package ca.docilecraft;

import org.bukkit.entity.Player;
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

//If Statements
		
		if(permission(e.getPlayer(), "Prefixed.colour")){
			message = e.getMessage().replace("%", "%%").replace("&", "§");
		}else {
			message = e.getMessage().replace("%", "%%");
		}
		
//Formatting
		
		e.setFormat(plugin.getConfig().getString("format").replace("-Prefix", prefix(e.getPlayer().getName(), world)).replace("-Suffix", suffix(e.getPlayer().getName(), world)).replace("-Player", player).replace("&", "§").replace("-Message", message));
	}
	
	
	
	
	public String prefix(String player, String world){
		if(plugin.getServer().getPluginManager().isPluginEnabled("Vault")){
			String prefix = Prefixed.chat.getPlayerPrefix(world, player);
			if(prefix == null) prefix = "";
			return prefix;
		}else if(plugin.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			String prefix = PermissionsEx.getPermissionManager().getUser(player).getPrefix(world);
			if(prefix == null) prefix = "";
			return prefix;
		}
		return "";
	}
	
	
	
	
	public String suffix(String player, String world){
		if(plugin.getServer().getPluginManager().isPluginEnabled("Vault")){
			String suffix = Prefixed.chat.getPlayerSuffix(world, player);
			if(suffix == null) suffix = "";
			return suffix;
		}else if(plugin.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			String suffix = PermissionsEx.getPermissionManager().getUser(player).getSuffix(world);
			if(suffix == null) suffix = "";
			return suffix;
		}
		return "";
	}
	
	
	
	
	public boolean permission(Player player, String permission){
		if(plugin.getServer().getPluginManager().isPluginEnabled("Vault")){
			boolean haspermission = Prefixed.permission.has(player, permission);
			return haspermission;
		}else if(plugin.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			boolean haspermission = PermissionsEx.getPermissionManager().getUser(player).has(permission);
			return haspermission;
		}
		return false;
	}
}
