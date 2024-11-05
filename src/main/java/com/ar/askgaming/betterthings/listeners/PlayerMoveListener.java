package com.ar.askgaming.betterthings.Listeners;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Managers.FatigueManager;
import com.ar.askgaming.betterthings.Managers.ThirstManager;

public class PlayerMoveListener implements Listener{

    private BetterThings plugin;
    public PlayerMoveListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        ThirstManager t = plugin.getThirstManager();
        if (t.hasEnabled(p) && t.isInEnabledWorld(p)) {
            
            boolean b = plugin.getConfig().getBoolean("thirst.amount_on_fire_blocks");
            if (b) {
                switch (p.getLocation().getBlock().getType()) {
                    case LAVA:                   
                    case FIRE:    
                    case CAMPFIRE:
                    case SOUL_FIRE:
                    case SOUL_CAMPFIRE:
                    case MAGMA_BLOCK:                   
                    case TORCH:
                        p.setFireTicks(20);      
                        t.increase(p, 1);
                        break;
                    default:
                        break;
                }
            }
        }
    }
				
	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e){
		
		Player p = (Player) e.getPlayer();
				
        FatigueManager f = plugin.getFatigueManager();

        if (f.hasEnabled(p) && f.isInEnabledWorld(p)) {

            f.increase(p, 2);
            int at = plugin.getConfig().getInt("fatigue.deny_actions.cancel_sprint_below");

            if (f.getCurrent(p) < at) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 200, 1));
                e.setCancelled(true);
                p.sendMessage("No puedes correr si estas muy cansado!");
            }
        }
	}  
}
