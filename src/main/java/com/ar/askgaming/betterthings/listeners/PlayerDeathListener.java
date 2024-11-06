package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class PlayerDeathListener implements Listener{

    private BetterThings plugin;
    public PlayerDeathListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        plugin.getThirstManager().setAttribute(p, 200);
        plugin.getFatigueManager().setAttribute(p, 250);
    }
    
}
