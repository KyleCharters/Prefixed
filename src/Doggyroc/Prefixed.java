package Doggyroc;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Prefixed extends JavaPlugin {
	
//Startup
	
	public void onEnable(){
		
		PluginManager PluginM = getServer().getPluginManager();
		
		if(!PluginM.isPluginEnabled("PermissionsEx")){
			System.out.print(" Plugin needs PermissionsEx to run!");
			PluginM.disablePlugin(this);
			return;
		}else{
			PluginM.registerEvents(new ChatListener(), this);
			System.out.print("[Prefixed] Enabled!");
		}
	}
	
	public void onDisable(){
		System.out.print("[Prefixed] Disabled!");
	}
}