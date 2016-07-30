package ca.docilecraft;

import static ca.docilecraft.CustomsHandler.PlayerOption.COLOR;
import static ca.docilecraft.CustomsHandler.PlayerOption.PREFIX;
import static ca.docilecraft.CustomsHandler.PlayerOption.SUFFIX;
import static ca.docilecraft.color.PColor.AQUA;
import static ca.docilecraft.color.PColor.BLACK;
import static ca.docilecraft.color.PColor.BLUE;
import static ca.docilecraft.color.PColor.BOLD;
import static ca.docilecraft.color.PColor.DARKAQUA;
import static ca.docilecraft.color.PColor.DARKBLUE;
import static ca.docilecraft.color.PColor.DARKGRAY;
import static ca.docilecraft.color.PColor.DARKGREEN;
import static ca.docilecraft.color.PColor.DARKRED;
import static ca.docilecraft.color.PColor.GOLD;
import static ca.docilecraft.color.PColor.GRAY;
import static ca.docilecraft.color.PColor.GREEN;
import static ca.docilecraft.color.PColor.ITALIC;
import static ca.docilecraft.color.PColor.OBFUSCATED;
import static ca.docilecraft.color.PColor.PINK;
import static ca.docilecraft.color.PColor.PURPLE;
import static ca.docilecraft.color.PColor.RED;
import static ca.docilecraft.color.PColor.RESET;
import static ca.docilecraft.color.PColor.STRIKE;
import static ca.docilecraft.color.PColor.UNDERLINE;
import static ca.docilecraft.color.PColor.WHITE;
import static ca.docilecraft.color.PColor.YELLOW;
import static ca.docilecraft.color.PColor.translateColors;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import ca.docilecraft.color.PColorCollection;

public class PrefixedCommand implements CommandExecutor{
	Prefixed prefixed = Prefixed.instance;
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg){
		if(!sender.hasPermission("Prefixed.admin")){
			send(sender, "You do not have permission to use this command.");
			return true;
		}
		if(arg.length == 0){
			showHelp(sender);
			return true;
		}
		
		if(arg[0].equalsIgnoreCase("reload")){
			prefixed.reload();
			PrefixedListener.TabListener.reloadTabList();
			sender.sendMessage("-----");
			send(sender, "Config reloaded, here are the new nodes:");
			send(sender, "Multiple Prefixes/Suffixes = "+PrefixedConfig.useMultiple);
			send(sender, "Use Display Names = "+PrefixedConfig.useDisplayName);
			send(sender, "Use Custom Tab List Names = "+PrefixedConfig.useTabList);
			send(sender, "Use Player Mention = "+PrefixedConfig.usePlayerMention);
			send(sender, "Use Player Mention Sound = "+PrefixedConfig.usePlayerMentionSound);
			send(sender, "All player customs have been updated");
			sender.sendMessage("-----");
			return true;
		}
		if(arg[0].equalsIgnoreCase("help")){
			showHelp(sender);
			return true;
		}
		if(arg[0].equalsIgnoreCase("colors")){
			send(sender, "Valid color codes:");
			senda(sender, "&r or &RESET ("+BOLD+"Ex"+RESET+"ample)");
			senda(sender, "&o or &ITALIC ("+ITALIC+"Example"+RESET+")");
			senda(sender, "&n or &UNDERLINE ("+UNDERLINE+"Example"+RESET+")");
			senda(sender, "&m or &STRIKE ("+STRIKE+"Example"+RESET+")");
			senda(sender, "&l or &BOLD ("+BOLD+"Example"+RESET+")");
			senda(sender, "&k or &OBFUSCATED ("+OBFUSCATED+"Example"+RESET+")");
			senda(sender, "");
			senda(sender, WHITE+"&f or &WHITE");
			senda(sender, YELLOW+"&e or &YELLOW");
			senda(sender, PINK+"&d or &PINK");
			senda(sender, RED+"&c or &RED");
			senda(sender, AQUA+"&b or &AQUA");
			senda(sender, GREEN+"&a or &GREEN");
			senda(sender, BLUE+"&9 or &BLUE");
			senda(sender, DARKGRAY+"&8 or &DARKGRAY");
			senda(sender, GRAY+"&7 or &GRAY");
			senda(sender, GOLD+"&6 or &DARKGREEN");
			senda(sender, PURPLE+"&5 or &PURPLE");
			senda(sender, DARKRED+"&4 or &DARKRED");
			senda(sender, DARKAQUA+"&3 or &DARKAQUA");
			senda(sender, DARKGREEN+"&2 or &DARKGREEN");
			senda(sender, DARKBLUE+"&1 or &DARKBLUE");
			senda(sender, BLACK+"&0 or &BLACK");
			return true;
		}
		if(arg[0].equalsIgnoreCase("prefix")){
			if(arg.length == 2){
				UUID uuid = CustomsHandler.getUUIDStartsWith(arg[1]);
				if(uuid != null){
					String name = CustomsHandler.getName(uuid);
					String prefix = CustomsHandler.get(uuid, PREFIX);
					if(prefix != null){
						send(sender, name+"'s prefix: "+(prefix.contains("&") ? translateColors(prefix)+" ("+prefix+")" : prefix));
						return true;
					}
					send(sender, name+" does not have a prefix.");
					return true;
				}
				send(sender, "Unknown player.");
				return true;
			}
			send(sender, "Usage: /prefixed prefix <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("suffix")){
			if(arg.length == 2){
				UUID uuid = CustomsHandler.getUUIDStartsWith(arg[1]);
				if(uuid != null){
					String name = CustomsHandler.getName(uuid);
					String suffix = CustomsHandler.get(uuid, SUFFIX);
					if(suffix != null){
						send(sender, name+"'s suffix: "+(suffix.contains("&") ? translateColors(suffix)+" ("+suffix+")" : suffix));
						return true;
					}
					send(sender, name+" does not have a suffix.");
					return true;
				}
				send(sender, "Unknown player.");
				return true;
			}
			send(sender, "Usage: /prefixed suffix <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("color")){
			if(arg.length == 2){
				UUID uuid = CustomsHandler.getUUIDStartsWith(arg[1]);
				if(uuid != null){
					String name = CustomsHandler.getName(uuid);
					PColorCollection color = new PColorCollection(CustomsHandler.get(uuid, COLOR));
					if(!color.isEmpty()){
						String names = color.getNames();
						
						send(sender, name+"'s color: "+color+names+RESET+" ("+names+")");
						return true;
					}
					send(sender, name+" does not have a color.");
					return true;
				}
				send(sender, "Unknown player.");
				return true;
			}
			send(sender, "Usage: /prefixed color <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("vault")){
			if(arg.length == 3 && arg[1].equalsIgnoreCase("prefix")){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[2])){
						String prefix = HooksHandler.vaultChat.getPlayerPrefix(player);
						send(sender, player.getName()+"'s vault prefix: "+(prefix.contains("&") ? translateColors(prefix)+WHITE+" ("+prefix+")" : prefix));
						return true;
					}
				}
				send(sender, "Player must be online to check vault.");
				return true;
			}
			if(arg.length == 3 && arg[1].equalsIgnoreCase("suffix")){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[2])){
						String suffix = HooksHandler.vaultChat.getPlayerSuffix(player);
						send(sender, player.getName()+"'s vault suffix: "+(suffix.contains("&") ? translateColors(suffix)+WHITE+" ("+suffix+")" : suffix));
						return true;
					}
				}
				send(sender, "Player must be online to check vault.");
				return true;
			}
			send(sender, "Usage: /prefixed vault [prefix/suffix] <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("set")){
			if(arg.length == 4 && arg[1].equalsIgnoreCase("prefix")){
				String prefix = translateColors(arg[3]);
				UUID uuid = CustomsHandler.getUUID(arg[2]);
				
				if(uuid != null){
					CustomsHandler.set(uuid, PREFIX, arg[3]);
					send(sender, "User "+CustomsHandler.getName(uuid)+"'s prefix set to: "+prefix);
					return true;
				}
				
				if(CustomsHandler.setPlayer(arg[2], arg[3], null, null)){
					send(sender, "User "+arg[2]+" created with prefix "+prefix);
				}else{
					send(sender, "No such user in existence.");
				}
				return true;
			}
			if(arg.length == 4 && arg[1].equalsIgnoreCase("suffix")){
				String suffix = translateColors(arg[3]);
				UUID uuid = CustomsHandler.getUUID(arg[2]);
				
				if(uuid != null){
					CustomsHandler.set(uuid, SUFFIX, arg[3]);
					send(sender, "User "+CustomsHandler.getName(uuid)+"'s suffix set to: "+suffix);
					return true;
				}
				
				if(CustomsHandler.setPlayer(arg[2], null, arg[3], null)){
					send(sender, "User "+arg[2]+" created with suffix "+suffix);
				}else{
					send(sender, "No such user in existence.");
				}
				return true;
			}
			if(arg.length == 4 && arg[1].equalsIgnoreCase("color")){
				PColorCollection pcolorbuilder = new PColorCollection(arg[3]);
				if(pcolorbuilder.isEmpty()){
					send(sender, "Not a valid color.");
					return true;
				}
				String color = pcolorbuilder.getNames();
				
				UUID uuid = CustomsHandler.getUUID(arg[2]);
				if(uuid != null){
					CustomsHandler.set(uuid, COLOR, color);
					send(sender, "User "+CustomsHandler.getName(uuid)+"'s color set to: "+pcolorbuilder+color+RESET+" ("+color+")");
					PrefixedListener.TabListener.reloadTabList();
					return true;
				}
				
				if(CustomsHandler.setPlayer(arg[2], null, null, color)){
					send(sender, "User "+arg[2]+" created with color "+new PColorCollection(color)+color+RESET+" ("+color+")");
				}else{
					send(sender, "No such user in existence.");
				}
				return true;
			}
			send(sender, "Usage: /prefixed set [prefix/suffix/color] <playername> <newval>");
			return true;
		}
		
		send(sender, RED+": Unknown argument.");
		return true;
	}
	
	private void showHelp(CommandSender sender){
		sender.sendMessage("-----");
		send(sender, " Prefixed version "+prefixed.getDescription().getVersion());
		send(sender, " (Optional) [Either] <Variable>");
		send(sender, " Prefixed Commands:");
		send(sender, " /prefixed (vault) [prefix/suffix] <playername> : Shows player's prefix/suffix");
		send(sender, " /prefixed color <playername> : Shows player's color");
		send(sender, " /prefixed colors : Shows valid color codes");
		send(sender, " /prefixed set [prefix/suffix/color] <playername> <new> : Sets player's prefix/suffix");
		send(sender, " /prefixed reload : Reloads plugin");
		send(sender, " /prefixed help : Shows this menu");
		sender.sendMessage("-----");
	}
	
	String title = BLACK+"Pre"+RED+"fixed"+WHITE+": ";
	
	private void send(CommandSender sender, String message){
		sender.sendMessage(title+message);
	}
	
	private void senda(CommandSender sender, String message){
		sender.sendMessage(message);
	}
}
