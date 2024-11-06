package com.ar.askgaming.betterthings.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Drinks.Items.DrinkType;

public class PrepareItemCraftListener implements Listener{

    private BetterThings plugin;
    public PrepareItemCraftListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if (event.getRecipe() != null && event.getRecipe().getResult().isSimilar(plugin.getItems().getDrink(DrinkType.LEMONADE))) {
            ItemStack[] matrix = event.getInventory().getMatrix();
            boolean hasValidLimeDye = false;

            for (ItemStack item : matrix) {
                if (item != null && item.getType() == Material.LIME_DYE) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta.getPersistentDataContainer().has(plugin.getItems().getKey())){
                    
                        hasValidLimeDye = true;
                        break;
                    }
                }
            }

            if (!hasValidLimeDye) {
                event.getInventory().setResult(null); // Cancel the crafting result
            }
        }
    }
}
