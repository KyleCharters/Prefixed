package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PrefixedCommand implements CommandExecutor{
	PrefixedLite prefixed = PrefixedLite.instance;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg){
		if(!sender.hasPermission("PrefixedLite.admin")){
			send(sender, "You do not have permission to use this command.");
			return true;
		}
		if(arg.length == 0){
			showHelp(sender);
			return true;
		}
		
		if(arg[0].equalsIgnoreCase("reload")){
			prefixed.reload();
			sender.sendMessage("-----");
			send(sender, "Config reloaded, here are the new nodes:");
			send(sender, "Multiple Prefixes/Suffixes = "+PrefixedLite.useMultiple);
			send(sender, "Use Display Names = "+PrefixedLite.useDisplayName);
			send(sender, "Use Custom Tab List Names = "+PrefixedLite.useTabList);
			send(sender, "Use Player Mention = "+PrefixedLite.usePlayerMention);
			send(sender, "Use Player Mention Sound = "+PrefixedLite.usePlayerMentionSound);
			sender.sendMessage("-----");
			return true;
		}
		if(arg[0].equalsIgnoreCase("help")){
			showHelp(sender);
			return true;
		}
		if(arg[0].equalsIgnoreCase("colors")){
			send(sender, "Valid color codes:");
			senda(sender, "&r or &RESET ("+PColor.BOLD+"Ex"+PColor.RESET+"ample)");
			senda(sender, "&o or &ITALIC ("+PColor.ITALIC+"Example"+PColor.RESET+")");
			senda(sender, "&n or &UNDERLINE ("+PColor.UNDERLINE+"Example"+PColor.RESET+")");
			senda(sender, "&m or &STRIKE ("+PColor.STRIKE+"Example"+PColor.RESET+")");
			senda(sender, "&l or &BOLD ("+PColor.BOLD+"Example"+PColor.RESET+")");
			senda(sender, "&k or &OBFUSCATED ("+PColor.OBFUSCATED+"Example"+PColor.RESET+")");
			senda(sender, "");
			senda(sender, PColor.WHITE+"&f or &WHITE");
			senda(sender, PColor.YELLOW+"&e or &YELLOW");
			senda(sender, PColor.PINK+"&d or &PINK");
			senda(sender, PColor.RED+"&c or &RED");
			senda(sender, PColor.AQUA+"&b or &AQUA");
			senda(sender, PColor.GREEN+"&a or &GREEN");
			senda(sender, PColor.BLUE+"&9 or &BLUE");
			senda(sender, PColor.DARKGRAY+"&8 or &DARKGRAY");
			senda(sender, PColor.GRAY+"&7 or &GRAY");
			senda(sender, PColor.GOLD+"&6 or &DARKGREEN");
			senda(sender, PColor.PURPLE+"&5 or &PURPLE");
			senda(sender, PColor.DARKRED+"&4 or &DARKRED");
			senda(sender, PColor.DARKAQUA+"&3 or &DARKAQUA");
			senda(sender, PColor.DARKGREEN+"&2 or &DARKGREEN");
			senda(sender, PColor.DARKBLUE+"&1 or &DARKBLUE");
			senda(sender, PColor.BLACK+"&0 or &BLACK");
			return true;
		}
		if(arg[0].equalsIgnoreCase("prefix")){
			if(arg.length == 2){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
						String prefix = PrefixedLite.vault.getPlayerPrefix(player);
						send(sender, player.getName()+"'s prefix: "+(prefix.contains("&") ? PColor.translateColorCodes(prefix)+PColor.WHITE+" ("+prefix+")" : prefix));
						return true;
					}
				}
				send(sender, "Player must be online to check prefix.");
				return true;
			}
			send(sender, "Usage: /prefixed prefix <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("suffix")){
			if(arg.length == 2){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
						String suffix = PrefixedLite.vault.getPlayerSuffix(player);
						send(sender, player.getName()+"'s suffix: "+(suffix.contains("&") ? PColor.translateColorCodes(suffix)+PColor.WHITE+" ("+suffix+")" : suffix));
						return true;
					}
				}
				send(sender, "Player must be online to check suffix.");
				return true;
			}
			send(sender, "Usage: /prefixed suffix <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("color")){
			if(arg.length == 2){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
						String color = PlayerInfo.getColor(player);
						send(sender, player.getName()+"'s color: "+color+PColor.toString(color));
						return true;
					}
				}
				send(sender, "Player must be online to check color");
				return true;
			}
			send(sender, "Usage: /prefixed color <playername>");
			return true;
		}
		
		send(sender, PColor.RED+": Unknown argument.");
		return true;
	}
	
	private void showHelp(CommandSender sender){
		sender.sendMessage("-----");
		send(sender, " PrefixedLite version "+prefixed.getDescription().getVersion());
		send(sender, " PrefixedLite Commands:");
		send(sender, " /prefixedlite [prefix/suffix] <playername> : Shows player's prefix/suffix");
		send(sender, " /prefixedlite color <playername> : Shows player's color");
		send(sender, " /prefixedlite colors : Shows valid color codes");
		send(sender, " /prefixedlite reload : Reloads plugin");
		send(sender, " /prefixedlite help : Shows this menu");
		sender.sendMessage("-----");
	}
	
	String title = PColor.BLACK+"Pre"+PColor.WHITE+"fixedLite: ";
	
	private void send(CommandSender sender, String message){
		sender.sendMessage(title+message);
	}
	
	private void senda(CommandSender sender, String message){
		sender.sendMessage(message);
	}
}
