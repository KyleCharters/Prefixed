package ca.docilecraft;

import static ca.docilecraft.color.PColor.GREEN;
import static ca.docilecraft.color.PColor.RESET;
import static ca.docilecraft.color.PColor.translateColorCodes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import ca.docilecraft.PrefixedAPI.PrefixedParameter;

public class MessageHandler{
	protected static ArrayList<PrefixedParameter> parameters = new ArrayList<PrefixedParameter>();
	
	protected static boolean loadParameter(PrefixedParameter parameter){
		if(PrefixedConfig.format.contains(parameter.name)){
			return parameters.add(parameter);
		}
		return false;
	}
	
	protected static String checkDefaultParameters(Player player, String format){
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
		
		
		return format;
	}
	
	protected static String digest(Player player, String message){
		String format = PrefixedConfig.format;
		
		//Calculate default parameters
		format = checkDefaultParameters(player, format);
		
		//Calculate PrefixedAPI parameters
		for(PrefixedParameter p : parameters)
			format = format.replace("-"+p.name, p.use(player, message));
		
		//Calculate player mentions
		message = digestMentions(message);
		
		//Replace message and add colors
		return translateColorCodes(format).replace("-Message", RESET+(player.hasPermission("Prefixed.chatincolor") ? translateColorCodes(message) : message));
	}
	
	private static String digestMentions(String message){
		if(PrefixedConfig.usePlayerMention){
			for(Player player : Bukkit.getOnlinePlayers()){
				String name = PlayerInfo.getName(player);
				
				if(message.toLowerCase().contains(name.toLowerCase())){
					message = message.replaceAll("(?i)"+name, GREEN+"@"+name+RESET);
					
					if(PrefixedConfig.usePlayerMentionSound){
						player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1f, 0.7f);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Prefixed.instance, new Runnable(){
							public void run(){
								player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1f, 1.05f);
							}
						}, 3l);
					}
				}
			}
		}
		return message;
	}
}
