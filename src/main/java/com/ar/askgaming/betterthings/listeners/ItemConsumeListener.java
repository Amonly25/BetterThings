package com.ar.askgaming.betterthings.Listeners;

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

        switch (item.getType()) {
            case MUSHROOM_STEW:
                plugin.getThirstManager().increase(p, plugin.getFiles().getItemsConfig().getInt("default_items.MUSHROOM_STEW.thirst_value"));	
                plugin.getFatigueManager().increase(p, plugin.getFiles().getItemsConfig().getInt("default_items.MUSHROOM_STEW.fatigue_value"));	
                break;
            case MILK_BUCKET:
                plugin.getThirstManager().increase(p, plugin.getFiles().getItemsConfig().getInt("default_items.MILK_BUCKET.thirst_value"));	
                plugin.getFatigueManager().increase(p, plugin.getFiles().getItemsConfig().getInt("default_items.MILK_BUCKET.fatigue_value"));	
                break;
  
            case POTION:

                if (item.hasItemMeta()) {
                        
                    PotionMeta pm = (PotionMeta) item.getItemMeta();
                    
                    if (pm.getBasePotionType() == PotionType.WATER) {
        
                        if (plugin.getItems().isDrink(item)){
                            plugin.getThirstManager().increase(p, plugin.getItems().thirstValue(item));
                            plugin.getFatigueManager().increase(p, plugin.getItems().fatigueValue(item));
                        } else {
                            // If not has the custom key, should be a simple water bottle
                            plugin.getThirstManager().increase(p, plugin.getFiles().getItemsConfig().getInt("WATER.thirst_value"));
                            plugin.getFatigueManager().increase(p, plugin.getFiles().getItemsConfig().getInt("WATER.fatigue_value"));
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
