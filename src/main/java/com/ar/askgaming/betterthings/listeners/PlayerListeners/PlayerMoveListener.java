package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;

public class PlayerMoveListener implements Listener{

    private BetterThings plugin;
    public PlayerMoveListener(BetterThings main) {
        plugin = main;
    }

    public static final Map<Player, Block> lastBlockStanding = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        Thirst t = plugin.getAttributeManager().getThirst();
        if (t.hasEnabled(p)) {
            
            boolean b = plugin.getConfig().getBoolean("thirst.increase_on_fire_blocks",true);
            if (b) {
                switch (p.getLocation().getBlock().getType()) {
                    case LAVA:                   
                    case FIRE:    
                    case CAMPFIRE:
                    case SOUL_FIRE:
                    case SOUL_CAMPFIRE:     
                    case SOUL_TORCH:         
                    case TORCH:
                        p.setFireTicks(20);      
                        t.decrease(p, 0.1);
                        break;
                    default:
                        break;
                }
            }
        }

        Block currentBlock = p.getLocation().getBlock();
        Block lastBlock = lastBlockStanding.get(p);

        //Simple Afk detector
        if (lastBlock == null || !lastBlock.equals(currentBlock)) {
            lastBlockStanding.put(p, currentBlock);
        }
    }
				
	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e){
		
		Player p = (Player) e.getPlayer();
				
        if (!e.isSprinting()) {
            return;
        }

        Fatigue f = plugin.getAttributeManager().getFatigue();
        Thirst t = plugin.getAttributeManager().getThirst();

        if (t.hasEnabled(p)) {
            t.decrease(p, 0.1);
        }

        if (f.hasEnabled(p)) {
            f.decrease(p, 0.1);
            double at = plugin.getConfig().getInt("fatigue.cancel_sprint_below",5);

            if (f.getAttribute(p) < at) {
                e.setCancelled(true);
               // plugin.getLogger().info("Sprint cancelled");
            }
        }
	}  
    @EventHandler
	public void onSprint(EntityToggleSwimEvent e){
		
		Entity entity = e.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player p = (Player) entity;
				
        if (!e.isSwimming()) {
            return;
        }

        Fatigue f = plugin.getAttributeManager().getFatigue();
        Thirst t = plugin.getAttributeManager().getThirst();
        
        if (t.hasEnabled(p)) {
            t.decrease(p, 0.1);
        }

        if (f.hasEnabled(p)) {
            f.decrease(p, 0.1);
            double at = plugin.getConfig().getInt("fatigue.cancel_swim_below",5);

            if (f.getAttribute(p) < at) {
                e.setCancelled(true);
            }
        }
	} 
}
