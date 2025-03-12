package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;

public class PlayerDeathListener implements Listener{

    private BetterThings plugin;
    public PlayerDeathListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        
        Thirst t = plugin.getAttributeManager().getThirst();
        t.setAttribute(p, t.getMaxAttribute());
        Fatigue f = plugin.getAttributeManager().getFatigue();
        f.setAttribute(p, f.getMaxAttribute());

    } 
}
