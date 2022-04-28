package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PrefixedLite extends JavaPlugin {
	protected static PrefixedLite instance;
	private PluginManager pluginM = getServer().getPluginManager();
	protected static Chat vault = null;
	
	/*
	 * Plugin startup
	 */
	public void onEnable(){
		instance = this;
		
		//Check for vault
		if(!pluginM.isPluginEnabled("Vault")){
			log(3, "PrefixedLite requires Vault to run.");
			log(3, "If you do not wish to use Vault, download Prefixed.");
			log(3, "To download Vault, go here: http://dev.bukkit.org/bukkit-plugins/vault/");
			return;
		}
		
		//Setup Config
		getConfig().options().copyDefaults(true);
		saveConfig();
		reload();
		
		//Register Events
		pluginM.registerEvents(new ChatListener(), this);
		if(useTabList){
			pluginM.registerEvents(new TabListener(), this);
		}
		
		//Setup Command
		getCommand("PrefixedLite").setExecutor(new PrefixedCommand());
		
		//Setup Vault
		setupVault();
		
		//Reporting to metrics
		try{
			new MetricsLite(this).start();
		}catch(IOException e){
			log(1, "Could not connect to Metrics!");
		}
		
		//Log enabled
		log(0, "Enabled!");
	}
	
	public void onDisable(){
		log(0, "Disabled!");
	}
	
	/*
	 * VAULT
	 */
	
	protected static void setupVault(){
		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null){
			vault = chatProvider.getProvider();
			
			PrefixedLite.log(0, "Sucessfully Hooked Into Vault!");
		}
	}
	
	/*
	 * CONFIG
	 */
	
	protected static String format;
	protected static boolean useMultiple;
	protected static boolean useDisplayName;
	protected static boolean useTabList;
	protected static boolean usePlayerMention;
	protected static boolean usePlayerMentionSound;
	
	protected void loadConfig(){
		format = getConfig().getString("format");
		useMultiple = getConfig().getBoolean("useMultiple");
		useDisplayName = getConfig().getBoolean("useDisplayName");
		useTabList = getConfig().getBoolean("useTabList");
		usePlayerMention = getConfig().getBoolean("usePlayerMention");
		usePlayerMentionSound = getConfig().getBoolean("usePlayerMentionSound");
	}
	
	public void saveConfigChanges(){
		getConfig().set("format", format);
		getConfig().set("useMultiple", useMultiple);
		getConfig().set("useDisplayName", useDisplayName);
		getConfig().set("useTabList", useTabList);
		getConfig().set("usePlayerMention", usePlayerMention);
		getConfig().set("usePlayerMentionSound", usePlayerMentionSound);
		super.saveConfig();
	}
	
	public void reload(){
		reloadConfig();
		loadConfig();
		
		if(useTabList)
			TabListener.reloadTabList();
	}

	/* 
	 * -----
	 */

	protected static void log(int level, String out){
		if(level == 0) Bukkit.getServer().getLogger().info("[PrefixedLite] "+out);
		if(level == 1) Bukkit.getServer().getLogger().warning("[PrefixedLite] "+out);
		if(level == 2) Bukkit.getServer().getLogger().severe("[PrefixedLite] "+out);
	}
}
