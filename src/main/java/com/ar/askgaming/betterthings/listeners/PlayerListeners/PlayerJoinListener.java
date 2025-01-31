package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class PlayerJoinListener implements Listener{

    private BetterThings plugin;
    public PlayerJoinListener(BetterThings main) {
        plugin = main;
    }
    
    @EventHandler()
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();
          
        plugin.getThirstManager().add(p);
        plugin.getFatigueManager().add(p);
    }
}
