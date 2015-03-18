package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	
	public static Chat chat = null;
	private PluginManager PluginM = getServer().getPluginManager();
	
	/*
	 * Plugin startup
	 */
	public void onEnable(){
		//Reporting to metrics
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			log(1, "Could not connect to Metrics!");
		}
		
		//Check if vault is running
		if(!PluginM.isPluginEnabled("Vault")){
			log(2, "This plugin requires Vault to run!");
			log(2, "Download Vault at http://dev.bukkit.org/server-mods/Vault");
			PluginM.disablePlugin(this);
			return;
		}
		setupChat();
		
		//Register Events
		PluginM.registerEvents(new PrefixedListener.ChatListener(), this);
		if(getConfig().getBoolean("useTabList"))
			PluginM.registerEvents(new PrefixedListener.TabListener(), this);
		
		//Setup Config
		getConfig().options().copyDefaults(true);
		saveConfig();
		PrefixedConfig.loadConfig(this);
		
		//Setup Command
		getCommand("Prefixed").setExecutor(new CommandHandler(this));
		
		log(0, "Enabled!");
	}
	
	public void onDisable(){
		chat = null;
		log(0, "Disabled!");
	}
	
	/* 
	 * Vault
	 * -----
	 */
	
	private void setupChat(){
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null){
			chat = chatProvider.getProvider();
			
			log(0, "Sucessfully Hooked Into Vault!");
		}
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
		PrefixedConfig.loadConfig(this);
		
		if(PrefixedConfig.useTabList)
			PrefixedListener.TabListener.reloadTabList();
	}
}