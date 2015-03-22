package ca.docilecraft;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.chat.Chat;

public class VaultManager{
	protected static boolean enabled = false;
	protected static Chat chat = null;
	
	protected static void setupChat(){
		RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null){
			chat = chatProvider.getProvider();
			enabled = true;
			
			Prefixed.log(0, "Sucessfully Hooked Into Vault!");
		}
	}
}
