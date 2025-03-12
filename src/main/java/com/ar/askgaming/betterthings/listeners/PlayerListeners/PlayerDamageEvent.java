package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;

public class PlayerDamageEvent implements Listener {

    private BetterThings plugin;
    public PlayerDamageEvent(BetterThings main) {
        plugin = main;
        
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            Thirst t = plugin.getAttributeManager().getThirst();
            Fatigue f = plugin.getAttributeManager().getFatigue();
            if (t.hasEnabled(p)) {
                t.decrease(p, 0.2);
            }
            if (f.hasEnabled(p)) {
                f.decrease(p, 0.2);
            }
        }
    }
}
