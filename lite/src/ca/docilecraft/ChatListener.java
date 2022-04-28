package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		if(event.isCancelled()) return;
		
		event.setFormat(digest(event.getPlayer(), event.getMessage()));
	}
	
	protected static String digest(Player player, String message){
		String format = PrefixedLite.format;
		
		//-Prefix parameter
		format = format.replace("-Prefix", PlayerInfo.getPrefix(player));
		
		//-Suffix parameter
		format = format.replace("-Suffix", PlayerInfo.getSuffix(player));
		
		//-Player parameter
		format = format.replace("-Player", PlayerInfo.getName(player));
		
		//-World parameter
		format = format.replace("-World", PlayerInfo.getWorld(player));
		
		//-IPAddress parameter
		format = format.replace("-IPAddress", PlayerInfo.getAddress(player));
		
		//-IPPort parameter
		format = format.replace("-IPPort", PlayerInfo.getPort(player));
		
		//Calculate player mentions
		if(PrefixedLite.usePlayerMention){
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()){
				String name = PlayerInfo.getName(onlinePlayer);
				
				if(message.toLowerCase().contains(name.toLowerCase())){
					message = message.replaceAll("(?i)"+name, PColor.GREEN+"@"+name+PColor.RESET);
					
					if(PrefixedLite.usePlayerMentionSound){
						onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.NOTE_PIANO, 1f, 0.7f);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(PrefixedLite.instance, () -> {
							onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.NOTE_PIANO, 1f, 1.05f);
						}, 3l);
					}
				}
			}
		}
		
		//Replace message and add colors
		return PColor.translateColorCodes(format).replace("-Message", PColor.WHITE+(player.hasPermission("PrefixedLite.chatincolor") ? PColor.translateColorCodes(message) : message));
	}
}
