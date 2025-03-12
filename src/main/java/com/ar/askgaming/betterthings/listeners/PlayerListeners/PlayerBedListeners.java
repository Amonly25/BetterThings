package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;

public class PlayerBedListeners implements Listener{

    private BetterThings plugin;
    public PlayerBedListeners(BetterThings main) {
        plugin = main;
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent e) {
        Player p = e.getPlayer();
    	
    	Fatigue f = plugin.getAttributeManager().getFatigue();
    	
        if (f.hasEnabled(p)) {
            if (plugin.getConfig().getBoolean("fatigue.hunger_after_sleep", true)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 1));
                p.sendMessage(plugin.getFiles().getLang("fatigue.leave_bed"));

            }
            f.setAttribute(p, f.getMaxAttribute());
        } 
    }

    @EventHandler()
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player p = (Player) e.getPlayer();
    	
    	Fatigue f = plugin.getAttributeManager().getFatigue();
    	
    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (e.getClickedBlock().getType().toString().contains("BED")) {
            	
            	if (f.hasEnabled(p)) {

            		if (p.getWorld().getTime() < 13000 && f.getAttribute(p) < f.getMaxAttribute()) {

                        if (plugin.getConfig().getBoolean("fatigue.force_sleep_in_day", true)){
                            p.sleep(e.getClickedBlock().getLocation(), true);
                        }
                    }
            	}
            }
        }	
    }
}
