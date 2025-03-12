package com.ar.askgaming.betterthings.Listeners.PlayerListeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Thirst;

public class PlayerInteractListener implements Listener {

    private BetterThings plugin;
    public PlayerInteractListener(BetterThings main) {
        plugin = main;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!p.isSneaking()) {
            return;
        }
        Thirst t = plugin.getAttributeManager().getThirst();
        if (!t.hasEnabled(p)){
            return;
        }
        if (e.getHand() == EquipmentSlot.OFF_HAND) {
            return;

        }
        
        ItemStack item = p.getInventory().getItemInMainHand();
        Block targetBlock = p.getTargetBlock(null, 5);

        if (targetBlock.getType() == Material.WATER) {

            if (item == null || item.getType() == Material.AIR) {

                if (e.getAction() == Action.RIGHT_CLICK_AIR|| e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    Boolean enabled = plugin.getConfig().getBoolean("drink_from_water_block.enable",true);
                    double value = plugin.getConfig().getInt("drink_from_water_block.value",4);
                    double chance = plugin.getConfig().getDouble("drink_from_water_block.chance_to_get_sick",30);

                    if (!enabled) {
                        return;
                    }
                    if (t.getAttribute(p) >= t.getMaxAttribute()) {
                        return;
                    }
                    p.sendMessage(plugin.getFiles().getLang("thirst.drink_from_water_block"));
                    t.increase(p, value);

                    double random = Math.random() * 100;
                    if (random < chance) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40, 1));
                    }
                }
            }
        }
    }
}
