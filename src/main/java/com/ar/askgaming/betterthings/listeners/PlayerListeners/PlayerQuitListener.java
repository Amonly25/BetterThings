package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

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
       plugin.getFiles().savePlayerData();
    }
}
