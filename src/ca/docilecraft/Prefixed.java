package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	
	public static Chat chat = null;
	public static FileConfiguration config;
	
//Startup
	
	public void onEnable(){
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			log(1, "Could not connect to Metrics!");
		}
		
		PluginManager PluginM = getServer().getPluginManager();
		
		if(!PluginM.isPluginEnabled("Vault")){
			log(2, "This plugin requires Vault to run!");
			log(2, "Download Vault at http://dev.bukkit.org/server-mods/Vault");
			PluginM.disablePlugin(this);
			return;
		}
		
		PluginM.registerEvents(new ChatListener(), this);
		getCommand("Prefixed").setExecutor(new CommandHandler(this));
		setupChat();
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		config = getConfig();
		
		if(getConfig().getBoolean("useTabList")){
			PluginM.registerEvents(new TabListener(), this);
		}
		
		log(0, "Enabled!");
	}
	
	public void onDisable(){
		log(0, "Disabled!");
	}
	
	/* 
	 * Vault
	 * -----
	 */
	
	private boolean setupChat(){
	RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
	if (chatProvider != null){
		chat = chatProvider.getProvider();
		
		log(0, "Sucessfully Hooked Into Vault!");
	}
	
	return (chat != null);
}

	/* 
	 * -----
	 */

	public void log(int level, String out){
		if(level == 0) getLogger().info(out);
		if(level == 1) getLogger().warning(out);
		if(level == 2) getLogger().severe(out);
	}
	
	public void reload(){
		reloadConfig();
		config = getConfig();
		
		TabListener.reloadTabList();
	}
}