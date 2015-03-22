package ca.docilecraft;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import ca.docilecraft.PrefixedAPI.PrefixedParameter;

public class MessageHandler{
	@SuppressWarnings ("serial")
	protected static ArrayList<PrefixedParameter> parameters = new ArrayList<PrefixedParameter>(){{
		//-Prefix parameter
		add(new PrefixedParameter("Prefix"){
			public String use(Player player, String message){return PlayerInfo.getPrefix(player);}
		});
		
		//-Suffix parameter
		add(new PrefixedParameter("Suffix"){
			public String use(Player player, String message){return PlayerInfo.getSuffix(player);}
		});
		
		//-Player parameter
		add(new PrefixedParameter("Player"){
			public String use(Player player, String message){return PlayerInfo.getName(player);}
		});
		
		//-World parameter
		add(new PrefixedParameter("World"){
			public String use(Player player, String message){return PlayerInfo.getWorld(player);}
		});
		
		//-IPAddress parameter
		add(new PrefixedParameter("IPAddress"){
			public String use(Player player, String message){return PlayerInfo.getAddress(player);}
		});
		
		//-IPPort parameter
		add(new PrefixedParameter("IPPort"){
			public String use(Player player, String message){return PlayerInfo.getPort(player);}
		});
	}};
	
	protected static String digest(Player player, String message){
		String s = PrefixedConfig.format;
		
		for(PrefixedParameter p : parameters){
			if(s.contains("-"+p.name)){
				s = s.replace("-"+p.name, p.use(player, message));
			}
		}
		
		return ChatColor.translateAlternateColorCodes('&', s).replace("-Message", ChatColor.WHITE+(player.hasPermission("Prefixed.Colour") ? ChatColor.translateAlternateColorCodes('&', message) : message));
	}
}
