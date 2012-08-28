package ca.docilecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class TagListener implements Listener{
	
	Prefixed main;
	
	public TagListener(Prefixed instance){
		main = instance;
	}
	
	@EventHandler
	public void onPlayerReciveNameTag(PlayerReceiveNameTagEvent event){
		if(main.getConfig().getBoolean("usenametag") == true){
			String player = event.getNamedPlayer().getName();
			event.setTag(colour(event.getNamedPlayer()) + player);
		}
	}
	
	public String colour(Player player){
		String ret = "";
		String N = "Prefixed.TAG.";
		if(player.hasPermission(N+"YELLOW"))ret = ret + "§e";
		if(player.hasPermission(N+"PINK"))ret = ret + "§d";
		if(player.hasPermission(N+"RED"))ret = ret + "§c";
		if(player.hasPermission(N+"AQUA"))ret = ret + "§b";
		if(player.hasPermission(N+"BGREEN"))ret = ret + "§a";
		if(player.hasPermission(N+"INDIGO"))ret = ret + "§9";
		if(player.hasPermission(N+"DGREY"))ret = ret + "§8";
		if(player.hasPermission(N+"GREY"))ret = ret + "§7";
		if(player.hasPermission(N+"GOLD"))ret = ret + "§6";
		if(player.hasPermission(N+"PURPLE"))ret = ret+ "§5";
		if(player.hasPermission(N+"DRED"))ret = ret + "§4";
		if(player.hasPermission(N+"DAQUA"))ret = ret + "§3";
		if(player.hasPermission(N+"DGREEN"))ret = ret + "§2";
		if(player.hasPermission(N+"DBLUE"))ret = ret + "§1";
		if(player.hasPermission(N+"BLACK"))ret = ret + "§0";
		if(player.hasPermission(N+"RANDOM"))ret = ret + "§k";
		if(player.hasPermission(N+"BOLD"))ret = ret + "§l";
		if(player.hasPermission(N+"STRIKE"))ret = ret+ "§n";
		if(player.hasPermission(N+"UNDERLINE"))ret = ret + "§m";
		if(player.hasPermission(N+"ITALIC"))ret = ret + "§0";
		return ret;
	}
	
}
