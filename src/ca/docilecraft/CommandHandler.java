package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandHandler implements CommandExecutor{
	public CommandHandler(Prefixed prefixed){
		this.prefixed = prefixed;
	}
	
	Prefixed prefixed;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg){
		System.out.println(arg.length);
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
		if(arg[0].equalsIgnoreCase("prefix")){
			if(arg.length == 2){
				for(String key : PrefixedConfig.getPlayers().getKeys(false)){
					if(arg[1].equalsIgnoreCase(key)){
						String prefix = CustomsHandler.getPrefix(key);
						send(sender, key+"'s prefix: "+(prefix.contains("&") ? PColor.translateColorCodes(prefix)+" ("+prefix+")" : prefix));
						return true;
					}
				}
				send(sender, "Unknown player.");
				return true;
			}
			send(sender, "Usage: /prefixed prefix <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("suffix")){
			if(arg.length == 2){
				for(String key : PrefixedConfig.getPlayers().getKeys(false)){
					if(arg[1].equalsIgnoreCase(key)){
						String suffix = CustomsHandler.getSuffix(key);
						send(sender, key+"'s suffix: "+(suffix.contains("&") ? PColor.translateColorCodes(suffix)+" ("+suffix+")" : suffix));
						return true;
					}
				}
				send(sender, "Unknown player.");
				return true;
			}
			send(sender, "Usage: /prefixed suffix <playername>");
			return true;
		}
		if(arg[0].equalsIgnoreCase("vault")){
			if(arg.length == 3 && arg[1].equalsIgnoreCase("prefix")){
				for(Player player : Bukkit.getOnlinePlayers()){
					if(StringUtil.startsWithIgnoreCase(player.getName(), arg[2])){
						String prefix = VaultManager.chat.getPlayerPrefix(player);
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
						String suffix = VaultManager.chat.getPlayerSuffix(player);
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
				
				for(String key : PrefixedConfig.getPlayers().getKeys(false)){
					if(arg[2].equalsIgnoreCase(key)){
						CustomsHandler.setPrefix(key, prefix);
						send(sender, "User "+key+"'s prefix set to: "+prefix);
						return true;
					}
				}
				
				if(CustomsHandler.setPlayer(arg[2], prefix, null)){
					send(sender, "User created with prefix "+prefix);
				}else{
					send(sender, "No such user in existance");
				}
				return true;
			}
			if(arg.length == 4 && arg[1].equalsIgnoreCase("suffix")){
				String suffix = PColor.translateTextColors(arg[3]);
				
				for(String key : PrefixedConfig.getPlayers().getKeys(false)){
					if(arg[2].equalsIgnoreCase(key)){
						CustomsHandler.setSuffix(key, suffix);
						send(sender, "User "+key+"'s suffix set to: "+suffix);
						return true;
					}
				}
				
				if(CustomsHandler.setPlayer(arg[2], null, suffix)){
					send(sender, "User created with suffix "+suffix);
				}else{
					send(sender, "No such user in existance");
				}
				return true;
			}
			send(sender, "Usage: /prefixed set [prefix/suffix] <playername> <newval>");
			return true;
		}
		
		send(sender, PColor.RED+": Unknown argument.");
		return true;
	}
	
	String title = PColor.BLACK+"Pre"+PColor.RED+"fixed"+PColor.WHITE+": ";
	
	private void showHelp(CommandSender sender){
		sender.sendMessage("-----");
		sender.sendMessage(title+": Prefixed version "+prefixed.getDescription().getVersion());
		sender.sendMessage(title+": Prefixed Commands:");
		sender.sendMessage(title+": /prefixed (vault) [prefix/suffix] <playername> : Shows player's prefix/suffix");
		sender.sendMessage(title+": /prefixed set [prefix/suffix] <playername> <newval> : Sets player's prefix/suffix");
		sender.sendMessage(title+": /prefixed reload : Reloads plugin");
		sender.sendMessage(title+": /prefixed help : Shows this menu");
		sender.sendMessage("-----");
	}
	
	private void send(CommandSender sender, String message){
		sender.sendMessage(title+message);
	}
}
