package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.ar.askgaming.betterthings.BetterThings;

public class InventoryClickListener implements Listener {
    
    private BetterThings plugin;
    public InventoryClickListener(BetterThings main) {
        plugin = main;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().equals(plugin.getBarShop().getInv())) {
            e.setCancelled(true);

            if (!e.getClickedInventory().equals(plugin.getBarShop().getInv())) {
                return;
            }
                    
            ItemStack item = e.getCurrentItem();
            
            if (item == null || item.getType().equals(Material.AIR)) {
                return;
            }

            if (!plugin.getItems().isDrink(item)) {
                return;
            }
            
            ItemStack cloned = item.clone();
            cloned.setAmount(1);
            double cost = plugin.getItems().getCost(cloned);

            plugin.getBarShop().buyToVault(p, item, cost);
        }
    }
}
