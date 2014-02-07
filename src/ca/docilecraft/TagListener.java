package ca.docilecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class TagListener implements Listener{
	
	@EventHandler
	public void onPlayerReciveNameTag(AsyncPlayerReceiveNameTagEvent e){
		Player player = e.getNamedPlayer();
		e.setTag(Get.playerColours(player) + player.getName());
	}
}
