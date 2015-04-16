package ca.docilecraft;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	protected static Prefixed instance;
	private PluginManager pluginM = getServer().getPluginManager();
	
	/*
	 * Plugin startup
	 */
	public void onEnable(){
		instance = this;
		
		//Setup Config
		getConfig().options().copyDefaults(true);
		saveConfig();
		PrefixedConfig.reload();
		CustomsHandler.checkPlayers();
		
		//Register Events
		pluginM.registerEvents(new PrefixedListener.ChatListener(), this);
		if(PrefixedConfig.useTabList)
			pluginM.registerEvents(new PrefixedListener.TabListener(), this);
		
		//Setup Command
		getCommand("Prefixed").setExecutor(new PrefixedCommand());
		
		//Check if vault is running
		if(pluginM.isPluginEnabled("Vault")){
			HooksHandler.setupHooks();
		}
		
		//Reporting to metrics
		try{
			new MetricsLite(this).start();
		}catch(IOException e){
			log(1, "Could not connect to Metrics!");
		}
		
		//Log enabled
		log(0, "Enabled!");
	}
	
	public void reload(){
		PrefixedConfig.reload();
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
