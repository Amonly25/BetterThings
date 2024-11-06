package com.ar.askgaming.betterthings.Listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Managers.AttributeManager;

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
        
        List<AttributeManager> managers = Arrays.asList(plugin.getFatigueManager(), plugin.getThirstManager());
        
        for (AttributeManager manager : managers) {
            if (manager.hasEnabled(p) && manager.isInEnabledWorld(p)) {
                int threshold = plugin.getConfig().getInt(manager.getConfigKey() + ".deny_actions.regain_health_below", 25);
                
                if (manager.getCurrent(p) < threshold) {
                    e.setCancelled(true);
                    if (!notifiedPlayers.contains(p)) {
                        p.sendMessage(plugin.getFiles().getLang(manager.getConfigKey() + ".no_regain"));
                        notifiedPlayers.add(p);
                        
                        plugin.getServer().getScheduler().runTaskLater(plugin, () -> notifiedPlayers.remove(p), 200L);
                    }
                }
            }
        }
    }
}  
}
