package betterthings.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import betterthings.Main;


public class DeathEvent implements Listener {

	
	static Main plugin;
	public DeathEvent(Main main) {
		plugin = main;
	}	
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void deathEvent(PlayerDeathEvent e) {
		
        Player p = (Player) e.getEntity();
        plugin.getConfig().set("players.thirst." + p.getUniqueId(), 0);
        plugin.getConfig().set("players.fatigue." + p.getUniqueId(), 0);
	}
}
