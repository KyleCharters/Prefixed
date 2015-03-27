package ca.docilecraft;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PrefixedCommand implements CommandExecutor{
	public PrefixedCommand(Prefixed prefixed){
		this.prefixed = prefixed;
	}
	
	Prefixed prefixed;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg){
		if(!sender.hasPermission("Prefixed.Admin")){
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
			send(sender, "Multiple Prefixes/Suffixes = "+PrefixedConfig.useMultiple);
			send(sender, "Use Display Names = "+PrefixedConfig.useDisplayName);
			send(sender, "Use Custom Tab List Names = "+PrefixedConfig.useTabList);
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
			sender.sendMessage("&r or &RESET ("+PColor.BOLD+"Ex"+PColor.RESET+"ample)");
			sender.sendMessage("&o or &ITALIC ("+PColor.ITALIC+"Example"+PColor.RESET+")");
			sender.sendMessage("&n or &UNDERLINE ("+PColor.UNDERLINE+"Example"+PColor.RESET+")");
			sender.sendMessage("&m or &STRIKE ("+PColor.STRIKE+"Example"+PColor.RESET+")");
			sender.sendMessage("&l or &BOLD ("+PColor.BOLD+"Example"+PColor.RESET+")");
			sender.sendMessage("&k or &OBFUSCATED ("+PColor.OBFUSCATED+"Example"+PColor.RESET+")");
			sender.sendMessage("");
			sender.sendMessage(PColor.WHITE+"&f or &WHITE");
			sender.sendMessage(PColor.YELLOW+"&e or &YELLOW");
			sender.sendMessage(PColor.PINK+"&d or &PINK");
			sender.sendMessage(PColor.RED+"&c or &RED");
			sender.sendMessage(PColor.AQUA+"&b or &AQUA");
			sender.sendMessage(PColor.GREEN+"&a or &GREEN");
			sender.sendMessage(PColor.BLUE+"&9 or &BLUE");
			sender.sendMessage(PColor.DARKGRAY+"&8 or &DARKGRAY");
			sender.sendMessage(PColor.GRAY+"&7 or &GRAY");
			sender.sendMessage(PColor.GOLD+"&6 or &DARKGREEN");
			sender.sendMessage(PColor.PURPLE+"&5 or &PURPLE");
			sender.sendMessage(PColor.DARKRED+"&4 or &DARKRED");
			sender.sendMessage(PColor.DARKAQUA+"&3 or &DARKAQUA");
			sender.sendMessage(PColor.DARKGREEN+"&2 or &DARKGREEN");
			sender.sendMessage(PColor.DARKBLUE+"&1 or &DARKBLUE");
			sender.sendMessage(PColor.BLACK+"&0 or &BLACK");
			return true;
		}
		if(arg[0].equalsIgnoreCase("prefix")){
			if(arg.length == 2){
				UUID uuid = CustomsHandler.getUUIDStartsWith(arg[1]);
				if(uuid != null){
					String name = CustomsHandler.getName(uuid);
					String prefix = CustomsHandler.getPrefix(uuid);
					if(prefix != null){
						send(sender, name+"'s prefix: "+(prefix.contains("&") ? PColor.translateColorCodes(prefix)+" ("+prefix+")" : prefix));
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
					String suffix = CustomsHandler.getSuffix(uuid);
					if(suffix != null){
						send(sender, name+"'s suffix: "+(suffix.contains("&") ? PColor.translateColorCodes(suffix)+" ("+suffix+")" : suffix));
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
					String color = CustomsHandler.getColor(uuid);
					if(color != null){
						send(sender, name+"'s color: "+PColor.getCodeFromString(color)+color+PColor.RESET+" ("+color+")");
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
						String prefix = HooksHandler.vault.getPlayerPrefix(player);
						send(sender, player.getName()+"'s vault prefix: "+(prefix.contains("&") ? PColor.translateColorCodes(prefix)+PColor.WHITE+" ("+prefix+")" : prefix));
						return true;
					}
				}
				send(sender, "Player must be online to check vault.");
				return true;
			}
			if(arg.length == 3 && arg[1].equalsIgnoreCase("suffix")){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[2])){
						String suffix = HooksHandler.vault.getPlayerSuffix(player);
						send(sender, player.getName()+"'s vault suffix: "+(suffix.contains("&") ? PColor.translateColorCodes(suffix)+PColor.WHITE+" ("+suffix+")" : suffix));
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
				String prefix = PColor.translateTextColors(arg[3]);
				UUID uuid = CustomsHandler.getUUID(arg[2]);
				
				if(uuid != null){
					CustomsHandler.setPrefix(uuid, prefix);
					send(sender, "User "+CustomsHandler.getName(uuid)+"'s prefix set to: "+prefix);
					return true;
				}
				
				if(CustomsHandler.setPlayer(arg[2], prefix, null, null)){
					send(sender, "User created with prefix "+prefix);
				}else{
					send(sender, "No such user in existence.");
				}
				return true;
			}
			if(arg.length == 4 && arg[1].equalsIgnoreCase("suffix")){
				String suffix = PColor.translateTextColors(arg[3]);
				UUID uuid = CustomsHandler.getUUID(arg[2]);
				
				if(uuid != null){
					CustomsHandler.setSuffix(uuid, suffix);
					send(sender, "User "+CustomsHandler.getName(uuid)+"'s suffix set to: "+suffix);
					return true;
				}
				
				if(CustomsHandler.setPlayer(arg[2], null, suffix, null)){
					send(sender, "User created with suffix "+suffix);
				}else{
					send(sender, "No such user in existence.");
				}
				return true;
			}
			if(arg.length == 4 && arg[1].equalsIgnoreCase("color")){
				String color = arg[3].toUpperCase();
				for(String s : color.split(",")){
					if(!PColor.isValidColor(s)){
						send(sender, "Not a valid color.");
						return true;
					}
				}
				
				UUID uuid = CustomsHandler.getUUID(arg[2]);
				
				if(uuid != null){
					CustomsHandler.setColor(uuid, color);
					send(sender, "User "+CustomsHandler.getName(uuid)+"'s color set to: "+PColor.getCodeFromString(color)+color+PColor.RESET+" ("+color+")");
					return true;
				}
				
				if(CustomsHandler.setPlayer(arg[2], null, null, color)){
					send(sender, "User created with color "+PColor.getCodeFromString(color)+color+PColor.RESET+" ("+color+")");
				}else{
					send(sender, "No such user in existence.");
				}
			}
			send(sender, "Usage: /prefixed set [prefix/suffix/color] <playername> <newval>");
			return true;
		}
		
		send(sender, PColor.RED+": Unknown argument.");
		return true;
	}
	
	String title = PColor.BLACK+"Pre"+PColor.RED+"fixed"+PColor.WHITE+": ";
	
	private void showHelp(CommandSender sender){
		sender.sendMessage("-----");
		send(sender, " Prefixed version "+prefixed.getDescription().getVersion());
		send(sender, " Prefixed Commands:");
		send(sender, " /prefixed (vault) [prefix/suffix] <playername> : Shows player's prefix/suffix");
		send(sender, " /prefixed color <playername> : Shows player's color");
		send(sender, " /prefixed set [prefix/suffix/color] <playername> <newval> : Sets player's prefix/suffix");
		send(sender, " /prefixed reload : Reloads plugin");
		send(sender, " /prefixed help : Shows this menu");
		send(sender, " /prefixed colors : Shows valid color codes");
		sender.sendMessage("-----");
	}
	
	private void send(CommandSender sender, String message){
		sender.sendMessage(title+message);
	}
}
