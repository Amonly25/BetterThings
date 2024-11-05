package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class PlaceBreakBlockListener implements Listener{

    private BetterThings plugin;
    public PlaceBreakBlockListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler
    public void onPlaceBreakBlock(BlockPlaceEvent e) {
        Player p = e.getPlayer();
   
    }

    @EventHandler
    public void onPlaceBreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        
    }

}
