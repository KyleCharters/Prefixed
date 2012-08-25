package ca.docilecraft;

import java.io.IOException;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	
	String P = "[Prefixed] ";
	
//Startup
	
	public void onEnable(){
		
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} catch (IOException e) {
		    System.out.print(P + "Could not connect to Metrics!");
		}
		
		PluginManager PluginM = getServer().getPluginManager();
		
		if(PluginM.isPluginEnabled("PermissionsEx")){
			PluginM.registerEvents(new ChatListener(this), this);
			getConfig().options().copyDefaults(true);
			saveConfig();
			System.out.print(P + "Hooked Into PermissionsEx");
			System.out.print(P + "Enabled!");
			return;
		}else if(PluginM.isPluginEnabled("Vault")){
			PluginM.registerEvents(new ChatListener(this), this);
			setupChat();
			setupPermissions();
			getConfig().options().copyDefaults(true);
			saveConfig();
			System.out.print(P + "Hooked Into Vault!");
			System.out.print(P + "Enabled!");
			return;
		}else{
			System.out.print(P + "This plugin needs Vault or PermissionsEx to run!");
			PluginM.disablePlugin(this);
			return;
		}
	}
	public void onDisable(){
		System.out.print(P + "Disabled!");
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
}