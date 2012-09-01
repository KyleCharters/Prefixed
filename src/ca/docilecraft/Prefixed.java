package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;

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
		
		if(PluginM.isPluginEnabled("Vault")){
			PluginM.registerEvents(new ChatListener(this), this);
			setupChat();
			getConfig().options().copyDefaults(true);
			saveConfig();
			log(0, "Hooked Into Vault!");
		}else{
			log(2, "This plugin needs Vault to run!");
			log(2, "Download Vault at http://dev.bukkit.org/server-mods/Vault");
			PluginM.disablePlugin(this);
			return;
		}
		
		if(getConfig().getBoolean("useTabList")){
			PluginM.registerEvents(new TabListener(this), this);
		}
		
		if(getConfig().getBoolean("useSpam")){
			PluginM.registerEvents(new SpamListener(this), this);
		}
		
		if(PluginM.isPluginEnabled("TagAPI") && getConfig().getBoolean("useNameTag")){
			PluginM.registerEvents(new TagListener(this), this);
			log(0, "Hooked into TagAPI!");
		}
		
		log(0, "Enabled!");
	}
	
	public void onDisable(){
		log(0, "Disabled!");
	}
	
	public static Chat chat = null;
	
    private boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null){
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
    
	public void log(int level, String out){
		if(level == 0){
			getLogger().info(out);
		}
		if(level == 1){
			getLogger().warning(out);
		}
		if(level == 2){
			getLogger().severe(out);
		}
	}
}