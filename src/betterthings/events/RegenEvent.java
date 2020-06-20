package betterthings.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import betterthings.Main;

public class RegenEvent implements Listener {
	
	static Main plugin;
	public RegenEvent(Main main) {
		plugin = main;
	}	
	
	@EventHandler
	public void regen(EntityRegainHealthEvent e) {
		
		if (e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			int currentthirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
			int currentfatigue = plugin.getConfig().getInt("players.fatigue." + p.getUniqueId());
			
			double randDoublet = Math.random();
			
				if (e.getRegainReason() == RegainReason.SATIATED) {
					if (currentthirst > 200) {
						e.setCancelled(true);
						
						if (randDoublet <= 0.30D) {	
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.regenthirst")));
						}
					}
					if (currentfatigue > 200) {
						e.setCancelled(true);
						
						if (randDoublet <= 0.30D) {	
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.regenfatigue")));
						}
					}
			}
		}
	}
}
