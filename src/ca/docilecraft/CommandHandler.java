package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandHandler implements CommandExecutor{
	public CommandHandler(Prefixed prefixed){
		main = prefixed;
	}
	
	private String Title = PColor.BLACK+"Pre"+PColor.RED+"fixed"+PColor.WHITE;
	
	Prefixed main;
	
	private void showHelp(CommandSender sender){
		sender.sendMessage("-----");
		sender.sendMessage(Title+": Prefixed version "+main.getDescription().getVersion());
		sender.sendMessage(Title+": Prefixed Commands:");
		sender.sendMessage(Title+": /prefixed (vault)[prefix/suffix] <playername> : Shows the prefix or suffix of a player.");
		sender.sendMessage(Title+": /prefixed [setprefix/setsuffix] <playername> <newvar> : Sets prefix or suffix of a player.");
		sender.sendMessage(Title+": /prefixed reload : Reloads plugin.");
		sender.sendMessage(Title+": /prefixed help : Shows this menu.");
		sender.sendMessage("-----");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		//Check Permission
		if(sender.hasPermission("Prefixed.Admin")){
			//Check if command has 1 argument
			if(arg.length == 1){
				//Check if argument equals reload
				if(arg[0].equalsIgnoreCase("reload")){
					main.reload();
					sender.sendMessage("-----");
					sender.sendMessage(Title+": Config reloaded, here are the new nodes:");
					sender.sendMessage(Title+": Chat format = "+PrefixedConfig.format);
					sender.sendMessage(Title+": Using multiple prefixes = "+PrefixedConfig.useMultiple);
					sender.sendMessage(Title+": Using display names = "+PrefixedConfig.useDisplayName);
					sender.sendMessage(Title+": Using custom Tab list names = "+PrefixedConfig.useTabList);
					sender.sendMessage(Title+": All player customs have been updated");
					sender.sendMessage("-----");
					return true;
				}else if(arg[0].equalsIgnoreCase("help")){
					showHelp(sender);
					return true;
				}
			//Check if command h as 2 arguments
			}else if(arg.length == 2){
				//Check if argument equals prefix
				if(arg[0].equalsIgnoreCase("prefix")){
					for(String key : PrefixedConfig.getPlayers().getKeys(false)){
						if(arg[1].equalsIgnoreCase(key)){
							String prefix = CustomsHandler.getPrefix(key);
							sender.sendMessage(Title+": "+key+"'s prefix: "+(prefix.contains("&") ? PColor.translateColorCodes(prefix)+" ("+prefix+")" : prefix));
							return true;
						}
					}
				//Check if argument equals suffix
				}else if(arg[0].equalsIgnoreCase("suffix")){
					for(String key : PrefixedConfig.getPlayers().getKeys(false)){
						if(arg[1].equalsIgnoreCase(key)){
							String suffix = CustomsHandler.getSuffix(key);
							sender.sendMessage(Title+": "+key+"'s suffix: "+(suffix.contains("&") ? PColor.translateColorCodes(suffix)+" ("+suffix+")" : suffix));
							return true;
						}
					}
				}else if(arg[0].equalsIgnoreCase("vaultprefix")){
					for(Player player : Bukkit.getOnlinePlayers()){
						if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
							String prefix = VaultManager.chat.getPlayerPrefix(player);
							sender.sendMessage(Title+": "+player.getName()+"'s vault prefix: "+(prefix.contains("&") ? PColor.translateColorCodes(prefix)+PColor.WHITE+" ("+prefix+")" : prefix));
							return true;
						}
					}
					sender.sendMessage(Title+": Player must be online to check vault.");
					return true;
				}else if(arg[0].equalsIgnoreCase("vaultsuffix")){
					for(Player player : Bukkit.getOnlinePlayers()){
						if(StringUtil.startsWithIgnoreCase(player.getName(), arg[1])){
							String suffix = VaultManager.chat.getPlayerSuffix(player);
							sender.sendMessage(Title+": "+player.getName()+"'s vault prefix: "+(suffix.contains("&") ? PColor.translateColorCodes(suffix)+PColor.WHITE+" ("+suffix+")" : suffix));
							return true;
						}
					}
					sender.sendMessage(Title+": Player must be online to check vault.");
					return true;
				}
				
				sender.sendMessage(Title+PColor.RED+": Unknown player.");
				return true;
			}else if(arg.length == 3){
				if(arg[0].equalsIgnoreCase("setprefix")){
					for(String key : PrefixedConfig.getPlayers().getKeys(false)){
						if(arg[1].equalsIgnoreCase(key)){
							CustomsHandler.setPrefix(key, arg[2]);
							sender.sendMessage(Title+": User "+key+"'s prefix set to: "+arg[2]);
							return true;
						}
					}
					if(CustomsHandler.setPlayer(arg[1], arg[2], null))
						sender.sendMessage(Title+": User created with prefix "+arg[2]);
					else
						sender.sendMessage(Title+": No such user in existance");
					return true;
				}else if(arg[0].equalsIgnoreCase("setsuffix")){
					for(String key : PrefixedConfig.getPlayers().getKeys(false)){
						if(arg[1].equalsIgnoreCase(key)){
							CustomsHandler.setSuffix(key, arg[2]);
							sender.sendMessage(Title+": User "+key+"'s suffix set to: "+arg[2]);
							return true;
						}
					}
					if(CustomsHandler.setPlayer(arg[1], null, arg[2]))
						sender.sendMessage(Title+": User created with suffix "+arg[2]);
					else
						sender.sendMessage(Title+": No such user in existance");
					return true;
				}
			//Check if command has no arguments
			}else if(arg.length == 0){
				showHelp(sender);
				return true;
			}
			sender.sendMessage(Title+PColor.RED+": Unknown argument.");
			return true;
		}
		sender.sendMessage(Title+PColor.RED+": You do not have permission to use this command.");
		return true;
	}
}
