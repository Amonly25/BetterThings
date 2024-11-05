package com.ar.askgaming.betterthings.Drinks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.ar.askgaming.betterthings.BetterThings;

public class Items {

    private BetterThings plugin;
    public Items(BetterThings main) {
        plugin = main;
    }

    public enum DrinkType {
        WATER, MILK, JUICE, COFFEE, WINE, LEMONADE, GOD_JUICE
    }

    public ItemStack getDrink(DrinkType drinkType){

        FileConfiguration cfg = plugin.getFiles().getItemsConfig();

        ItemStack d = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) d.getItemMeta();
        meta.setBasePotionType(PotionType.WATER);

        NamespacedKey key = new NamespacedKey(plugin, "drink_type");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, drinkType.toString());

        List<String> lore = cfg.getStringList(drinkType.toString()+".lore");
        String name = cfg.getString(drinkType.toString()+".name","Unknown");
        Color color = getColorFromConfig(drinkType.toString()+".color");

        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setColor(color);

        switch (drinkType) {
            case GOD_JUICE:
                    PotionEffect vel = new PotionEffect(PotionEffectType.ABSORPTION, 3000, 1);
					meta.addCustomEffect(vel, true);
                 break;
            default:
                break;
        }
        d.setItemMeta(meta);
        return d;
    }    
    private Color getColorFromConfig(String path) {
        FileConfiguration cfg = plugin.getFiles().getItemsConfig();
        String colorString = cfg.getString(path);
        if (colorString != null) {
            try {
                return Color.fromRGB(
                    Integer.valueOf(colorString.substring(1, 3), 16),
                    Integer.valueOf(colorString.substring(3, 5), 16),
                    Integer.valueOf(colorString.substring(5, 7), 16)
                );
            } catch (NumberFormatException e) {
                plugin.getLogger().info("Invalid color format for " + path + ": " + colorString);
            }
        }
        return Color.WHITE; // Valor predeterminado en caso de error
    }
}
