package com.ar.askgaming.betterthings.Listeners;

import java.util.jar.Attributes.Name;

import javax.xml.stream.events.Namespace;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import com.ar.askgaming.betterthings.BetterThings;

public class ItemConsumeListener implements Listener {

    private BetterThings plugin;
    public ItemConsumeListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler()
    public void onConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item.getType().equals(Material.MILK_BUCKET)) {     								
            plugin.getThirstManager().increase(p, 50);				
        }
        if (item.hasItemMeta() && item.getType().equals(Material.POTION)) {
        			
            PotionMeta pm = (PotionMeta) item.getItemMeta();
            
            if (pm.getBasePotionType() == PotionType.WATER) {
                Bukkit.broadcastMessage("Is water");
                plugin.getThirstManager().increase(p, 40);
            }
        }
    }
}
