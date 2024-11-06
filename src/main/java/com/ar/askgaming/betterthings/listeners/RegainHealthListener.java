package com.ar.askgaming.betterthings.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Managers.FatigueManager;

public class RegainHealthListener implements Listener {

    private BetterThings plugin;
    public RegainHealthListener(BetterThings main) {
        plugin = main;
    }

    private List<Player> notifiedPlayers = new ArrayList<>();

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            FatigueManager f = plugin.getFatigueManager();

            if (f.hasEnabled(p) && f.isInEnabledWorld(p)) {
                int at = plugin.getConfig().getInt("fatigue.deny_actions.regain_health_below",25);
                
                if (f.getCurrent(p) < at) {
                    e.setCancelled(true);
                    if (!notifiedPlayers.contains(p)) {
                        p.sendMessage("No regenerarás vida si tu fatiga es menor a " + at);
                        notifiedPlayers.add(p);
                        
                        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                            @Override
                            public void run() {
                                notifiedPlayers.remove(p);
                            }
                        }, 200L); 
                    }
                }
            }
        }
    }  
}
