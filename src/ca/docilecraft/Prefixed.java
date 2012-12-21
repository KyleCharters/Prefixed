package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	
//Startup
	
	public void onEnable(){
		
		try {
		Metrics metrics = new Metrics(this);
		metrics.start();
		} catch (IOException e) {
		log(1, "Could not connect to Metrics!");
		}
		
		PluginManager PluginM = getServer().getPluginManager();
		
		//I'm starting the engine! Vroom! Vroom!
		if(PluginM.isPluginEnabled("Vault")){
			PluginM.registerEvents(new ChatListener(this), this);
			setupChat();
			
			getConfig().options().copyDefaults(true);
			saveConfig();
			
			log(0, "Hooked Into Vault!");
		}else{
			log(2, "This plugin requires Vault to run!");
			log(2, "Download Vault at http://dev.bukkit.org/server-mods/Vault");
			PluginM.disablePlugin(this);
			return;
		}
		
		//To use Tab List, Or to not use Tab List. That is the question.
		if(getConfig().getBoolean("useTabList")){
			PluginM.registerEvents(new TabListener(this), this);
		}
		
		//Look! I caught something!
		if(PluginM.isPluginEnabled("TagAPI") && getConfig().getBoolean("useNameTag")){
			PluginM.registerEvents(new TagListener(this), this);
			log(0, "Hooked into TagAPI!");
		}
		
		log(0, "Enabled!");
	}
	
	public void onDisable(){
		log(0, "Disabled!");
	}
	
	/* Vault
	 * -----
	 */
	
	public static Chat chat = null;
	
	private boolean setupChat(){
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
	if (chatProvider != null){
		chat = chatProvider.getProvider();
	}
	
	return (chat != null);
}

	/* -----
	 * Vault
	 */

	public synchronized String getPlayerColours(Player player){
		String ret = "";
		if(player.hasPermission("Prefixed.COLOUR.YELLOW"))ret = "§e";
		else if(player.hasPermission("Prefixed.COLOUR.PINK"))ret = "§d";
		else if(player.hasPermission("Prefixed.COLOUR.RED"))ret = "§c";
		else if(player.hasPermission("Prefixed.COLOUR.AQUA"))ret = "§b";
		else if(player.hasPermission("Prefixed.COLOUR.BGREEN"))ret = "§a";
		else if(player.hasPermission("Prefixed.COLOUR.INDIGO"))ret = "§9";
		else if(player.hasPermission("Prefixed.COLOUR.DGREY"))ret = "§8";
		else if(player.hasPermission("Prefixed.COLOUR.GREY"))ret = "§7";
		else if(player.hasPermission("Prefixed.COLOUR.GOLD"))ret = "§6";
		else if(player.hasPermission("Prefixed.COLOUR.PURPLE"))ret = "§5";
		else if(player.hasPermission("Prefixed.COLOUR.DRED"))ret = "§4";
		else if(player.hasPermission("Prefixed.COLOUR.DAQUA"))ret = "§3";
		else if(player.hasPermission("Prefixed.COLOUR.DGREEN"))ret = "§2";
		else if(player.hasPermission("Prefixed.COLOUR.DBLUE"))ret = "§1";
		else if(player.hasPermission("Prefixed.COLOUR.BLACK"))ret = "§0";
		
		if(player.hasPermission("Prefixed.COLOUR.RANDOM")){
			ret = ret + "§k";
			return ret;
		}
		if(player.hasPermission("Prefixed.COLOUR.BOLD"))ret = ret + "§l";
		if(player.hasPermission("Prefixed.COLOUR.STRIKE"))ret = ret+ "§n";
		if(player.hasPermission("Prefixed.COLOUR.UNDERLINE"))ret = ret + "§m";
		if(player.hasPermission("Prefixed.COLOUR.ITALIC"))ret = ret + "§0";
		return ret;
	}

	public void log(int level, String out){
		if(level == 0) getLogger().info(out);
		if(level == 1) getLogger().warning(out);
		if(level == 2) getLogger().severe(out);
	}
}