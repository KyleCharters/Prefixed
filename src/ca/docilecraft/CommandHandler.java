package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandHandler implements CommandExecutor{
	public CommandHandler(Prefixed prefixed){
		main = prefixed;
	}
	
	private String Title = ChatColor.BLACK+"Pre"+ChatColor.RED+"fixed"+ChatColor.WHITE;
	
	Prefixed main;
	
	private void showHelp(CommandSender sender){
		sender.sendMessage(Title+": Prefixed version "+main.getDescription().getVersion());
		sender.sendMessage(Title+": Prefixed Commands:");
		sender.sendMessage(Title+": /prefixed [prefix/suffix] <playername> : Shows the prefix or suffix of a player.");
		sender.sendMessage(Title+": /prefixed [setprefix/setsuffix] <playername> <newvar> : Sets prefix or suffix of a player.");
		sender.sendMessage(Title+": /prefixed reload : reloads config.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		//Check Permission
		if(sender.hasPermission("Prefixed.Admin")){
			//Check if command has 1 argument
			if(arg.length == 1){
				//Check if argument equals reload
				if(arg[0].equalsIgnoreCase("reload")){
					main.reload();
					sender.sendMessage(Title+": Config reloaded, here are the new nodes:");
					sender.sendMessage(Title+": Chat format = "+PrefixedConfig.format);
					sender.sendMessage(Title+": Using multiple prefixes = "+PrefixedConfig.useMultiple);
					sender.sendMessage(Title+": Using display names = "+PrefixedConfig.useDisplayName);
					sender.sendMessage(Title+": Using custom Tab list names = "+PrefixedConfig.useTabList);
					sender.sendMessage(Title+": All User and Group customs have been updated");
					return true;
				}else if(arg[0].equalsIgnoreCase("help")){
					showHelp(sender);
					return true;
				}
			//Check if command h as 2 arguments
			}else if(arg.length == 2){
				//Check if argument equals prefix
				if(arg[0].equalsIgnoreCase("prefix")){
					for(Player player:Bukkit.getOnlinePlayers()){
						if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
							
							String prefix = PlayerInfo.getPrefix(player);
							sender.sendMessage(Title+": "+player.getName()+"'s prefix is: "+(prefix.contains("&") ? ChatColor.translateAlternateColorCodes('&', prefix)+"("+prefix+")" : prefix));
							return true;
						}
					}
				//Check if argument equals suffix
				}else if(arg[0].equalsIgnoreCase("suffix")){
					for(Player player:Bukkit.getOnlinePlayers()){
						if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
							
							String suffix = PlayerInfo.getSuffix(player);
							sender.sendMessage(Title+": "+player.getName()+"'s suffix is: "+(suffix.contains("&") ? ChatColor.translateAlternateColorCodes('&', suffix)+ "("+suffix+")" : suffix));
							return true;
						}
					}
				}
				sender.sendMessage(Title+ChatColor.RED+": Unknown player.");
				return true;
			}else if(arg.length == 3){
				if(arg[0].equalsIgnoreCase("setprefix")){
					CustomsHandler.setPrefix(arg[1], arg[2]);
					sender.sendMessage(Title+": User "+arg[1]+"'s prefix set to: "+arg[2]);
					return true;
				}else if(arg[0].equalsIgnoreCase("setsuffix")){
					CustomsHandler.setSuffix(arg[1], arg[2]);
					sender.sendMessage(Title+": User "+arg[1]+"'s suffix set to: "+arg[2]);
					return true;
				}
			//Check if command has no arguments
			}else if(arg.length == 0){
				showHelp(sender);
				return true;
			}
			sender.sendMessage(Title+ChatColor.RED+": Unknown argument.");
			return true;
		}
		sender.sendMessage(Title+ChatColor.RED+": You do not have permission to use this command.");
		return true;
	}
}
