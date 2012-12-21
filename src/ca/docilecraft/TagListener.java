package ca.docilecraft;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class TagListener implements Listener{
	
	Prefixed main;
	
	public TagListener(Prefixed instance){
		main = instance;
	}
	
	@EventHandler
	public void onPlayerReciveNameTag(PlayerReceiveNameTagEvent e){
		Player player = e.getNamedPlayer();
		e.setTag(main.getPlayerColours(player) + player.getName());
	}
}
