package ca.docilecraft;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	private PluginManager PluginM = getServer().getPluginManager();
	
	/*
	 * Plugin startup
	 */
	public void onEnable(){
		//Reporting to metrics
		try {
			new Metrics(this).start();
		} catch (IOException e) {
			log(1, "Could not connect to Metrics!");
		}
		
		//Register Events
		PluginM.registerEvents(new PrefixedListener.ChatListener(), this);
		if(getConfig().getBoolean("useTabList"))
			PluginM.registerEvents(new PrefixedListener.TabListener(), this);
		
		//Setup Config
		getConfig().options().copyDefaults(true);
		saveConfig();
		PrefixedConfig.reload(this);
		CustomsHandler.checkPlayers();
		
		//Check if vault is running
		if(PluginM.isPluginEnabled("Vault")){
			VaultManager.setupChat();
		}
		
		//Setup Command
		getCommand("Prefixed").setExecutor(new CommandHandler(this));
		
		//Log enabled
		log(0, "Enabled!");
	}
	
	public void reload(){
		PrefixedConfig.reload(this);
		CustomsHandler.checkPlayers();
		
		if(PrefixedConfig.useTabList)
			PrefixedListener.TabListener.reloadTabList();
	}
	
	public void onDisable(){
		log(0, "Disabled!");
	}

	/* 
	 * -----
	 */

	protected static void log(int level, String out){
		if(level == 0) Bukkit.getServer().getLogger().info("[Prefixed] "+out);
		if(level == 1) Bukkit.getServer().getLogger().warning("[Prefixed] "+out);
		if(level == 2) Bukkit.getServer().getLogger().severe("[Prefixed] "+out);
	}
}
