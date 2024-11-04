package com.ar.askgaming.betterthings.Listeners;

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

        Player p = e.getPlayer();
        
       if (plugin.getThirstManager().getMap().containsKey(p)){
            plugin.getThirstManager().getMap().remove(p);        
       }

       if (plugin.getFatigueManager().getMap().containsKey(p)){
            plugin.getFatigueManager().getMap().remove(p);        
       }
    }
}
