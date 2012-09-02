package ca.docilecraft;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SpamListener implements Listener {
	
	Prefixed main;
	
	public SpamListener(Prefixed instance){
		main = instance;
	}
	
//HashMaps :3
	HashMap<String, Boolean> isMuted = new HashMap<String, Boolean>();
	HashMap<String, Integer> warning = new HashMap<String, Integer>();
	HashMap<String, Long> last = new HashMap<String, Long>();
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerChat(final AsyncPlayerChatEvent e){
		final String pN = e.getPlayer().getName();
		//Checking if they are muted.
		if(isMuted.get(pN) != null && isMuted.get(pN)){
			e.getPlayer().sendMessage(ChatColor.RED+"You are muted.");
			e.setCancelled(true);
			return;
		}
		//Actual muting code.
		Long time = System.currentTimeMillis() / 100;
		if(last.get(e.getPlayer().getName()) != null){
			Long t =  time - last.get(pN);
			if(t <= main.getConfig().getInt("Spam.timeConsideredSpam")){
				//they are spamming
				if(warning.get(pN) == null) warning.put(pN, 0);
				if(warning.get(pN) == main.getConfig().getInt("Spam.warningsForSpam") - 1){
					isMuted.put(pN, true);
					// After (30) seconds or amount in config, set muted to false!
					main.getServer().getScheduler().scheduleAsyncDelayedTask(main, new Runnable(){
						@Override
						public void run() {
							//Reseting.
							isMuted.put(pN, false);
							warning.put(pN, 0);
							e.getPlayer().sendMessage(ChatColor.RED+"You are no longer muted.");
						}
					}, main.getConfig().getInt("Spam.muteTime") * 20);
				}else{
					warning.put(pN, warning.get(pN) + 1);
				}
			}
		}
		last.put(pN, time);
	}
}
