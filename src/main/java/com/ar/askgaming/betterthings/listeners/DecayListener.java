package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

import com.ar.askgaming.betterthings.BetterThings;

public class DecayListener implements Listener {

    private BetterThings plugin;
    public DecayListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler
	public void onDecay(LeavesDecayEvent e) {
		
		Block b = e.getBlock();
		double randDoublet = Math.random();
		
		if (randDoublet <= plugin.getConfig().getDouble("others.chance_decay_drop",0.3)) {	
			if (b.getType() == Material.BIRCH_LEAVES) {
		        b.getWorld().dropItemNaturally(b.getLocation(), plugin.getItems().getlemon());
		    }
		}		
	}
}
