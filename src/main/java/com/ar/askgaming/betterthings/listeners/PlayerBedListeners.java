package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class PlayerBedListeners implements Listener{

    private BetterThings plugin;
    public PlayerBedListeners(BetterThings main) {
        plugin = main;
    }
    @EventHandler   
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {
        Player p = e.getPlayer();
        
    }
    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent e) {
        Player p = e.getPlayer();
        
    }

}
