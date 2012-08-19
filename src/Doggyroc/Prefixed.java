package Doggyroc;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	
//Startup
	
	public void onEnable(){
		
		PluginManager PluginM = getServer().getPluginManager();
		
		if(!PluginM.isPluginEnabled("PermissionsEx")){
			System.out.print("[Prefixed] This plugin needs PermissionsEx to run!");
			PluginM.disablePlugin(this);
			return;
		}else{
			PluginM.registerEvents(new ChatListener(this), this);
			System.out.print("[Prefixed] Enabled!");
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	public void onDisable(){
		System.out.print("[Prefixed] Disabled!");
	}
}