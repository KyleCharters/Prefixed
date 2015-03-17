package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandHandler implements CommandExecutor{
	
	private String Title = ChatColor.RED+"Pre"+ChatColor.BLACK+"fixed"+ChatColor.WHITE;
	
	Prefixed main;
	
	public CommandHandler(Prefixed pre){
		main = pre;
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
					sender.sendMessage(Title+": Chat format = "+Prefixed.config.getString("format"));
					sender.sendMessage(Title+": Using multiple prefixes = "+Prefixed.config.getBoolean("useMultiple"));
					sender.sendMessage(Title+": Using display names = "+Prefixed.config.getString("useDisplayName"));
					sender.sendMessage(Title+": Using custom Tab list names = "+Prefixed.config.getString("useTabList"));
					return true;
				}
			//Check if command h as 2 arguments
			}else if(arg.length == 2){
				//Check if argument equals prefix
				if(arg[0].equalsIgnoreCase("prefix")){
					for(Player player:Bukkit.getOnlinePlayers()){
						if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
							
							String prefix = Get.chatPrefix(player);
							sender.sendMessage(Title+": "+player.getName()+"'s prefix is: "+(prefix.contains("&") ? ChatColor.translateAlternateColorCodes('&', prefix)+"("+prefix+")" : prefix));
							return true;
						}
					}
				//Check if argument equals suffix
				}else if(arg[0].equalsIgnoreCase("suffix")){
					for(Player player:Bukkit.getOnlinePlayers()){
						if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
							
							String suffix = Get.chatSuffix(player);
							sender.sendMessage(Title+": "+player.getName()+"'s suffix is: "+(suffix.contains("&") ? ChatColor.translateAlternateColorCodes('&', suffix)+ "("+suffix+")" : suffix));
							return true;
						}
					}
				}
				sender.sendMessage(Title+ChatColor.RED+": Unknown player.");
				return true;
			//Check if command has no arguments
			}else if(arg.length == 0){
				sender.sendMessage(Title+": Prefixed version "+main.getDescription().getVersion());
				sender.sendMessage(Title+": Prefixed Commands:");
				sender.sendMessage(Title+": /prefixed [prefix/suffix] <playername> : Shows the prefix or suffix of a player.");
				sender.sendMessage(Title+": /prefixed reload : reloads config.");
				return true;
			}
			sender.sendMessage(Title+ChatColor.RED+": Unknown argument.");
			return true;
		}
		sender.sendMessage(Title+ChatColor.RED+": You do not have permission to use this command.");
		return true;
	}
}
