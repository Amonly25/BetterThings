package com.ar.askgaming.betterthings.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Managers.FatigueManager;
import com.ar.askgaming.betterthings.Managers.ThirstManager;

public class PlaceBreakBlockListener implements Listener{

    private BetterThings plugin;
    public PlaceBreakBlockListener(BetterThings main) {
        plugin = main;
    }

    private List<Player> notifiedPlayers = new ArrayList<>();

    @EventHandler
    public void onPlaceBreakBlock(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        ThirstManager t = plugin.getThirstManager();

        if (t.hasEnabled(p) && t.isInEnabledWorld(p)) {
            int at = plugin.getConfig().getInt("thirst.deny_actions.nausea_on_place_blocks_below",20);
            
            if (t.getCurrent(p) < at) {
                if (!notifiedPlayers.contains(p)) {
                    p.sendMessage(plugin.getFiles().getLang("thirst.should_drink"));
                    notifiedPlayers.add(p);
                    
                    plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            notifiedPlayers.remove(p);
                        }
                    }, 200L); 
                }
                p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 100, 3));
            }
        }
    }

    @EventHandler
    public void onPlaceBreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();

        FatigueManager f = plugin.getFatigueManager();
        if (f.hasEnabled(p) && f.isInEnabledWorld(p)) {
            int at = plugin.getConfig().getInt("fatigue.minig_fatigue_below",60);
            
            if (f.getCurrent(p) < at) {

                if (!notifiedPlayers.contains(p)) {
                    p.sendMessage(plugin.getFiles().getLang("fatigue.should_rest"));
                    notifiedPlayers.add(p);
                    
                    plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            notifiedPlayers.remove(p);
                        }
                    }, 200L); 
                }
                p.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 100, 2));

            }
        } 
    }
}
