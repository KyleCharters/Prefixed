package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListener implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e){
		e.getPlayer().setPlayerListName(Get.playerNameTag(e.getPlayer()));
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onWorldChange(PlayerChangedWorldEvent e){
		e.getPlayer().setPlayerListName(Get.playerNameTag(e.getPlayer()));
	}
	
	public static void reloadTabList(){
		for(Player player:Bukkit.getOnlinePlayers()){
			player.setPlayerListName(Get.playerNameTag(player));
		}
	}
}
