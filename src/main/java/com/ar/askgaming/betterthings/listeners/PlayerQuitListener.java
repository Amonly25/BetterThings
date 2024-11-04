package com.ar.askgaming.betterthings.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class PlayerQuitListener implements Listener{

    private BetterThings plugin;
    public PlayerQuitListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler()
    public void onQuit(PlayerQuitEvent e) {
        //Save the Player's Thirst and Fatigue
        Player p = e.getPlayer();
        
       if (plugin.getThirstManager().get().containsKey(p)){
            plugin.getThirstManager().get().remove(p);        
       }

       if (plugin.getFatigueManager().get().containsKey(p)){
            plugin.getFatigueManager().get().remove(p);        
       }
    }
}
