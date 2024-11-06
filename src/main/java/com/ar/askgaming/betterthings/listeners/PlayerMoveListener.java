package com.ar.askgaming.betterthings.Listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.block.Block;
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

    private List<Player> notifiedPlayers = new ArrayList<>();

    public static final Map<Player, Block> lastBlockStanding = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        
        ThirstManager t = plugin.getThirstManager();
        if (t.hasEnabled(p) && t.isInEnabledWorld(p)) {
            
            boolean b = plugin.getConfig().getBoolean("thirst.amount_on_fire_blocks",true);
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
                        t.decrease(p, 1);
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
				
        if (p.isSprinting()) {
            return;
        }

        FatigueManager f = plugin.getFatigueManager();

        if (f.hasEnabled(p) && f.isInEnabledWorld(p)) {

            int at = plugin.getConfig().getInt("fatigue.deny_actions.cancel_sprint_below",50);

            if (f.getCurrent(p) < at) {
                e.setCancelled(true);
                f.decrease(p, 1);
                // Envía el mensaje solo si el jugador no ha sido notificado
                if (!notifiedPlayers.contains(p)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 20, 1));
                    p.sendMessage("No puedes correr si estás muy cansado!");
                    notifiedPlayers.add(p);
                } else {

                    notifiedPlayers.remove(p);
                }
            }
        }
	}  
}
