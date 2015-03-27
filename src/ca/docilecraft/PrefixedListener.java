package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PrefixedListener{
	public static class ChatListener implements Listener{
		
		@EventHandler(priority = EventPriority.HIGH)
		public void onPlayerChat(AsyncPlayerChatEvent event){
			if(event.isCancelled()) return;
			
			event.setFormat(MessageHandler.digest(event.getPlayer(), event.getMessage()));
		}
	}
	
	public static class TabListener implements Listener{
		
		@EventHandler(priority = EventPriority.NORMAL)
		public void onPlayerJoin(PlayerJoinEvent event){
			event.getPlayer().setPlayerListName(PlayerInfo.playerNameTag(event.getPlayer()));
		}
		
		@EventHandler(priority = EventPriority.NORMAL)
		public void onWorldChange(PlayerChangedWorldEvent event){
			event.getPlayer().setPlayerListName(PlayerInfo.playerNameTag(event.getPlayer()));
		}
		
		
		public static void reloadTabList(){
			for(Player player:Bukkit.getOnlinePlayers()){
				player.setPlayerListName(PlayerInfo.playerNameTag(player));
			}
		}
	}
}