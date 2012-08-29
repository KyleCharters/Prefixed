package ca.docilecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class TabListener implements Listener{
	
	Prefixed main;
	
	public TabListener(Prefixed instance){
		main = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if((colour(player) + player.getName() + "§f").length() > 14){
			player.setPlayerListName((colour(player) + player.getName()).substring(0, 13) + "-" + "§f");
		}else{
			player.setPlayerListName(colour(player) + player.getName() + "§f");
		}
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayer(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		if((colour(player) + player.getName() + "§f").length() > 14){
			player.setPlayerListName((colour(player) + player.getName()).substring(0, 13) + "-" + "§f");
		}else{
			player.setPlayerListName(colour(player) + player.getName() + "§f");
		}
	}
	
	public String colour(Player player){
		String ret = "";
		String N = "Prefixed.COLOUR.";
		if(player.hasPermission(N+"YELLOW"))ret = "§e";
		if(player.hasPermission(N+"PINK"))ret = "§d";
		if(player.hasPermission(N+"RED"))ret = "§c";
		if(player.hasPermission(N+"AQUA"))ret = "§b";
		if(player.hasPermission(N+"BGREEN"))ret = "§a";
		if(player.hasPermission(N+"INDIGO"))ret = "§9";
		if(player.hasPermission(N+"DGREY"))ret = "§8";
		if(player.hasPermission(N+"GREY"))ret = "§7";
		if(player.hasPermission(N+"GOLD"))ret = "§6";
		if(player.hasPermission(N+"PURPLE"))ret = "§5";
		if(player.hasPermission(N+"DRED"))ret = "§4";
		if(player.hasPermission(N+"DAQUA"))ret = "§3";
		if(player.hasPermission(N+"DGREEN"))ret = "§2";
		if(player.hasPermission(N+"DBLUE"))ret = "§1";
		if(player.hasPermission(N+"BLACK"))ret = "§0";
		if(player.hasPermission(N+"RANDOM")){
			ret = ret + "§k";
			return ret;
		}
		if(player.hasPermission(N+"BOLD"))ret = ret + "§l";
		if(player.hasPermission(N+"STRIKE"))ret = ret+ "§n";
		if(player.hasPermission(N+"UNDERLINE"))ret = ret + "§m";
		if(player.hasPermission(N+"ITALIC"))ret = ret + "§0";
		return ret;
	}
}
