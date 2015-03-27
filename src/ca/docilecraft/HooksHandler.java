package ca.docilecraft;

import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class HooksHandler{
	
	protected static void setupHooks(){
		setupVault();
	}
	
	
	protected static boolean vaultEnabled = false;
	protected static Chat vault = null;
	
	private static void setupVault(){
		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null){
			vault = chatProvider.getProvider();
			vaultEnabled = true;
			
			Prefixed.log(0, "Sucessfully Hooked Into Vault!");
		}
	}
	
	protected static boolean essentialsEnabled = false;
	
	@SuppressWarnings ("unused")
	private static void setupEssentials(){
		
	}
}
