package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;

public class ItemConsumeListener implements Listener {

    private BetterThings plugin;
    public ItemConsumeListener(BetterThings main) {
        plugin = main;
    }
    @EventHandler()
    public void onConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        Thirst thirst = plugin.getAttributeManager().getThirst();
        Fatigue fatigue = plugin.getAttributeManager().getFatigue();

        switch (item.getType()) {
            case MUSHROOM_STEW:
                thirst.increase(p, plugin.getFiles().getItemsConfig().getDouble("default_items.MUSHROOM_STEW.thirst_value"));	
                fatigue.increase(p, plugin.getFiles().getItemsConfig().getDouble("default_items.MUSHROOM_STEW.fatigue_value"));	
                break;
            case MILK_BUCKET:
                thirst.increase(p, plugin.getFiles().getItemsConfig().getDouble("default_items.MILK_BUCKET.thirst_value"));	
                fatigue.increase(p, plugin.getFiles().getItemsConfig().getDouble("default_items.MILK_BUCKET.fatigue_value"));	
                break;
  
            case POTION:

                if (item.hasItemMeta()) {
                        
                    PotionMeta pm = (PotionMeta) item.getItemMeta();
                    
                    if (pm.getBasePotionType() == PotionType.WATER) {
        
                        if (plugin.getItems().isDrink(item)){
                            thirst.increase(p, plugin.getItems().thirstValue(item));
                            fatigue.increase(p, plugin.getItems().fatigueValue(item));
                        } else {
                            // If not has the custom key, should be a simple water bottle
                            thirst.increase(p, plugin.getFiles().getItemsConfig().getDouble("WATER.thirst_value"));
                            fatigue.increase(p, plugin.getFiles().getItemsConfig().getDouble("WATER.fatigue_value"));
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
