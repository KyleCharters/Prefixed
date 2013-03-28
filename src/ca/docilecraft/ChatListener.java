package ca.docilecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		if(event.isCancelled()) return;
		
		event.setFormat(Get.chatFormat(event.getPlayer(), event.getMessage()));
	}
}
