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
	public static boolean vaultEnabled = false;
	
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
			vaultEnabled = true;
			PluginM.registerEvents(new ChatListener(), this);
			getCommand("Prefixed").setExecutor(new CommandHandler(this));
			setupChat();
			
			getConfig().options().copyDefaults(true);
			saveConfig();
			config = getConfig();
			
			log(0, "Hooked Into Vault!");
		}else{
			log(2, "This plugin requires Vault to run!");
			log(2, "Download Vault at http://dev.bukkit.org/server-mods/Vault");
			PluginM.disablePlugin(this);
			return;
		}
		
		//To use Tab List, Or to not use Tab List. That is the question.
		if(getConfig().getBoolean("useTabList")){
			PluginM.registerEvents(new TabListener(), this);
		}
		
		//Look! I caught something!
		if(PluginM.isPluginEnabled("TagAPI") && getConfig().getBoolean("useNameTag")){
			try {
	            Class.forName("org.kitteh.tag.AsyncPlayerReceiveNameTagEvent");
	        } catch (final ClassNotFoundException e) {
	            log(2, "You need a newer version of TagAPI!");
	            log(2, "Download it at http://dev.bukkit.org/server-mods/tag/");
	            this.getServer().getPluginManager().disablePlugin(this);
	            return;
	        }
			PluginM.registerEvents(new TagListener(), this);
			log(0, "Hooked into TagAPI!");
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
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
	if (chatProvider != null){
		chat = chatProvider.getProvider();
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