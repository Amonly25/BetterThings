package com.ar.askgaming.betterthings.Listeners;

import java.net.http.WebSocket.Listener;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.ar.askgaming.betterthings.Attribute.AttributeAbstract;
import com.ar.askgaming.betterthings.BetterThings;

public class RegainHealthListener implements Listener {

    private BetterThings plugin;
    public RegainHealthListener(BetterThings main) {
        plugin = main;
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            
            List<AttributeAbstract> managers = Arrays.asList(plugin.getAttributeManager().getThirst(), plugin.getAttributeManager().getFatigue());
            
            for (AttributeAbstract manager : managers) {
                if (manager.hasEnabled(p)) {
                    manager.decrease(p, 0.1);
                    double threshold = plugin.getConfig().getInt(manager.getConfigKey() + ".cancel_regain_health_below", 4);
                    
                    if (manager.getAttribute(p) < threshold) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }  
}

