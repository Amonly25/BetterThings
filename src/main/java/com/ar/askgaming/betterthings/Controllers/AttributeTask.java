package com.ar.askgaming.betterthings.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerMoveListener;

public class AttributeTask extends BukkitRunnable{

    private AttributeManager attributeManager;
    private final Map<Player, Block> lastBlockStanding;

    public AttributeTask(AttributeManager attributeManager) {
        this.attributeManager = attributeManager;
        lastBlockStanding = new HashMap<>();
        
        runTaskTimer(BetterThings.getInstance(), 0, 1200);
    }

    @Override
    public void run() {
        for (Player pl : Bukkit.getOnlinePlayers()){

            // Check if the player is standing on the same block
            if (lastBlockStanding.containsKey(pl)){
                if (lastBlockStanding.get(pl).equals(PlayerMoveListener.lastBlockStanding.get(pl))) {
                    return;
                }
            }
            lastBlockStanding.put(pl, pl.getLocation().getBlock());

            if (attributeManager.getFatigue().hasEnabled(pl)){
                attributeManager.getFatigue().decrease(pl, null);
            }
            if (attributeManager.getThirst().hasEnabled(pl)){
                attributeManager.getThirst().decrease(pl, null);
            }
        }
    }
}
