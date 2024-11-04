package com.ar.askgaming.betterthings.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class PlayerJoinListener implements Listener{

    private BetterThings plugin;
    public PlayerJoinListener(BetterThings main) {
        plugin = main;

        // Load all the Online Players

    }
    @EventHandler()
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
  
        //Load the Player's Thirst and Fatigue
        int thirstAmount = data.getInt(p.getName()+".thirst.amount",0);
        int fatigueAmount = data.getInt(p.getName()+".fatigue.amount",0);
        
        //Check if has enabled the Thirst and Fatigue
        plugin.getThirstManager().get().put(p, thirstAmount);
        plugin.getFatigueManager().get().put(p, fatigueAmount);
    }

}
