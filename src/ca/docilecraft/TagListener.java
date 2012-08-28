package ca.docilecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import ru.tehkode.permissions.bukkit.PermissionsEx;

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
		if(hasPerm(player, N+"YELLOW"))ret = ret + "§e";
		if(hasPerm(player, N+"PINK"))ret = ret + "§d";
		if(hasPerm(player, N+"RED"))ret = ret + "§c";
		if(hasPerm(player, N+"AQUA"))ret = ret + "§b";
		if(hasPerm(player, N+"BGREEN"))ret = ret + "§a";
		if(hasPerm(player, N+"INDIGO"))ret = ret + "§9";
		if(hasPerm(player, N+"DGREY"))ret = ret + "§8";
		if(hasPerm(player, N+"GREY"))ret = ret + "§7";
		if(hasPerm(player, N+"GOLD"))ret = ret + "§6";
		if(hasPerm(player, N+"PURPLE"))ret = ret+ "§5";
		if(hasPerm(player, N+"DRED"))ret = ret + "§4";
		if(hasPerm(player, N+"DAQUA"))ret = ret + "§3";
		if(hasPerm(player, N+"DGREEN"))ret = ret + "§2";
		if(hasPerm(player, N+"DBLUE"))ret = ret + "§1";
		if(hasPerm(player, N+"BLACK"))ret = ret + "§0";
		if(hasPerm(player, N+"RANDOM"))ret = ret + "§k";
		if(hasPerm(player, N+"BOLD"))ret = ret + "§l";
		if(hasPerm(player, N+"STRIKE"))ret = ret+ "§n";
		if(hasPerm(player, N+"UNDERLINE"))ret = ret + "§m";
		if(hasPerm(player, N+"ITALIC"))ret = ret + "§0";
		return ret;
	}
	
	public boolean hasPerm(Player player, String perm){
		if(main.getServer().getPluginManager().isPluginEnabled("PermissionsEx")){
			boolean haspermission = PermissionsEx.getPermissionManager().getUser(player).has(perm);
			return haspermission;
		}else if(main.getServer().getPluginManager().isPluginEnabled("Vault")){
			boolean haspermission = Prefixed.permission.has(player, perm);
			return haspermission;
		}
		return false;
	}
}
