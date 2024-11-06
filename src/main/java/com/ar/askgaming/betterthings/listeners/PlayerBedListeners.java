package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Managers.FatigueManager;

public class PlayerBedListeners implements Listener{

    private BetterThings plugin;
    public PlayerBedListeners(BetterThings main) {
        plugin = main;
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent e) {
        Player p = e.getPlayer();
    	
    	FatigueManager f = plugin.getFatigueManager();
    	
        if (f.hasEnabled(p) && f.isInEnabledWorld(p)) {
            if (plugin.getConfig().getBoolean("fatigue.hunger_after_sleep", true)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 1));
                p.sendMessage("Mh que hambre, a desayunar!");

            }
            f.setAttribute(p, 250);
        } 
    }

    @EventHandler()
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player p = (Player) e.getPlayer();
    	
    	FatigueManager f = plugin.getFatigueManager();
    	
    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (e.getClickedBlock().getType().toString().contains("BED")) {
            	
            	if (f.hasEnabled(p) && f.isInEnabledWorld(p)) {

            		if (p.getWorld().getTime() < 13000 && f.getCurrent(p) < 200) {

                        if (plugin.getConfig().getBoolean("fatigue.force_sleep_in_day", true)){
                            p.sleep(e.getClickedBlock().getLocation(), true);
                        }
                    }
            	}
            }
        }	
    }
}
