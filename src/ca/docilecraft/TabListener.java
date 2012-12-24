package ca.docilecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListener implements Listener{
	
	Prefixed main;
	
	public TabListener(Prefixed instance){
		main = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		
		String colours = main.getPlayerColours(player);
		if(colours.length() > 0){
			if((colours + player.getName() + "§f").length() > 16){
				player.setPlayerListName((colours + player.getName()).substring(0, 13) + "-" + "§f");
			}else{
				player.setPlayerListName(colours + player.getName() + "§f");
			}
		}
	}
	
	public void onWorldChange(PlayerChangedWorldEvent e){
		Player player = e.getPlayer();
		
		String colours = main.getPlayerColours(player);
		if(colours.length() > 0){
			if((colours + player.getName() + "§f").length() > 16){
				player.setPlayerListName((colours + player.getName()).substring(0, 13) + "-" + "§f");
			}else{
				player.setPlayerListName(colours + player.getName() + "§f");
			}
		}
	}
}
