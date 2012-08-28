package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

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
		
		if(PluginM.isPluginEnabled("PermissionsEx")){
			PluginM.registerEvents(new ChatListener(this), this);
			getConfig().options().copyDefaults(true);
			saveConfig();
			log(0, "Hooked Into PermissionsEx");
		}else if(PluginM.isPluginEnabled("Vault")){
			PluginM.registerEvents(new ChatListener(this), this);
			setupChat();
			setupPermissions();
			getConfig().options().copyDefaults(true);
			saveConfig();
			log(0, "Hooked Into Vault!");
		}else{
			log(2, "This plugin needs Vault or PermissionsEx to run!");
			PluginM.disablePlugin(this);
			return;
		}
		
		if(PluginM.isPluginEnabled("TagAPI") && (getConfig().getBoolean("usenametag") == true)){
			PluginM.registerEvents(new TagListener(this), this);
			log(0, "Hooked into TagAPI!");
		}
		
		log(0, "Enabled!");
	}
	
	public void onDisable(){
		log(0, "Disabled!");
	}
	
	public static Permission permission = null;
	public static Chat chat = null;
	
    private boolean setupChat(){
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null){
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }
    
    private boolean setupPermissions(){
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null){
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
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