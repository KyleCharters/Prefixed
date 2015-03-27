package ca.docilecraft;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import ca.docilecraft.PrefixedAPI.PrefixedParameter;

public class MessageHandler{
	protected static ArrayList<PrefixedParameter> parameters = new ArrayList<PrefixedParameter>();
	
	static{
		loadDefaultParameters();
	}
	
	protected static void loadDefaultParameters(){
		//-Prefix parameter
		parameters.add(new PrefixedParameter("Prefix"){
				public String use(Player player, String message){return PlayerInfo.getPrefix(player);}
			});
		
		//-Suffix parameter
		parameters.add(new PrefixedParameter("Suffix"){
				public String use(Player player, String message){return PlayerInfo.getSuffix(player);}
			});
		
		//-Player parameter
		parameters.add(new PrefixedParameter("Player"){
				public String use(Player player, String message){return PlayerInfo.getName(player);}
			});
		
		//-World parameter
		parameters.add(new PrefixedParameter("World"){
				public String use(Player player, String message){return PlayerInfo.getWorld(player);}
			});
		
		//-IPAddress parameter
		parameters.add(new PrefixedParameter("IPAddress"){
				public String use(Player player, String message){return PlayerInfo.getAddress(player);}
			});
		
		//-IPPort parameter
		parameters.add(new PrefixedParameter("IPPort"){
				public String use(Player player, String message){return PlayerInfo.getPort(player);}
			});
	}
	
	protected static String digest(Player player, String message){
		String s = PrefixedConfig.format;
		
		for(PrefixedParameter p : parameters){
			if(s.contains("-"+p.name)){
				s = s.replace("-"+p.name, p.use(player, message));
			}
		}
		
		return PColor.translateColorCodes(s).replace("-Message", PColor.WHITE+(player.hasPermission("Prefixed.color") ? PColor.translateColorCodes(message) : message));
	}
}
