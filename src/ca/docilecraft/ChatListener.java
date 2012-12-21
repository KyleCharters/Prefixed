package ca.docilecraft;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.ChatColor;
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
	public void onPlayerChat(AsyncPlayerChatEvent event){
		if(event.isCancelled()) return;
		
		event.setFormat(format(event.getPlayer(), event.getMessage()));
	}
	
	/*
	 * Methods!
	 */
	
	public String prefix(Player player){
		if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			Chat chat = Prefixed.chat;
			
			StringBuilder prefix = new StringBuilder().append(chat.getPlayerPrefix(player.getWorld(), player.getName())).append(ChatColor.WHITE);
			
			if(main.getConfig().getBoolean("useMultiple")){
				//if multiple suffixes are enabled
				for(String group : chat.getPlayerGroups(player)){
					String groupPre = chat.getGroupPrefix(player.getWorld(), group);
					if(!prefix.toString().contains(groupPre)) prefix.append(groupPre).append(ChatColor.WHITE);
				}
			}
			
			return prefix.toString();
		}
		return "";
	}

	public String suffix(Player player){
		if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			Chat chat = Prefixed.chat;
			
			StringBuilder suffix = new StringBuilder().append(chat.getPlayerSuffix(player.getWorld(), player.getName())).append(ChatColor.WHITE);
			
			if(main.getConfig().getBoolean("useMultiple")){
				//if multiple suffixes are enabled
				for(String group : chat.getPlayerGroups(player)){
					String groupSuf = chat.getGroupSuffix(player.getWorld(), group);
					if(!suffix.toString().contains(groupSuf)) suffix.append(groupSuf).append(ChatColor.WHITE);
				}
			}
			
			return suffix.toString();
		}
		return "";
	}
	
	public String format(Player player, String message){
		return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("format")
			.replace("-Prefix", prefix(player)) //Adding in suffix
			.replace("-Suffix", suffix(player)) //Adding in suffix
			.replace("-Player", main.getConfig().getBoolean("useDisplayName") ? player.getDisplayName() : player.getName()) //Adding in name [Check if using display name]
			.replace("-World", player.getWorld().getName())) //Adding with current world : End the colour formatting
			.replace("-Message", player.hasPermission("Prefixed.Colour")? ChatColor.translateAlternateColorCodes('&', message) : message); //Adding in the message [Check if player has permission to chat in colour]
	}
}
