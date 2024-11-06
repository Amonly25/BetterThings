package com.ar.askgaming.betterthings.Drinks;

import java.util.Collection;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.ar.askgaming.betterthings.BetterThings;

import net.md_5.bungee.api.ChatColor;

public class Items {

    private NamespacedKey key;

    public NamespacedKey getKey() {
        return key;
    }
    private BetterThings plugin;
    public Items(BetterThings main) {
        plugin = main;

        key = new NamespacedKey(plugin, "drink_type");
    }

    public enum DrinkType {
        WATER, MILK, JUICE, COFFEE, WINE, LEMONADE, GOD_JUICE
    }

    public ItemStack getDrink(DrinkType drinkType){

        FileConfiguration cfg = plugin.getFiles().getItemsConfig();

        ItemStack d = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) d.getItemMeta();
        meta.setBasePotionType(PotionType.WATER);

        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, drinkType.toString());

        List<String> lore = cfg.getStringList(drinkType.toString()+".lore");
        String name = cfg.getString(drinkType.toString()+".name","Unknown");
        Color color = getColorFromConfig(drinkType.toString()+".color");

        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            s = ChatColor.translateAlternateColorCodes('&', s);
            lore.set(i, s);
        }

        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
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
        if (colorString != null && colorString.matches("^#([A-Fa-f0-9]{6})$")) {
            try {
                return Color.fromRGB(
                    Integer.valueOf(colorString.substring(1, 3), 16),
                    Integer.valueOf(colorString.substring(3, 5), 16),
                    Integer.valueOf(colorString.substring(5, 7), 16)
                );
            } catch (NumberFormatException e) {
                plugin.getLogger().info("Invalid color format for " + path + ": " + colorString);
            }
        } else {
            plugin.getLogger().info("Invalid color format for " + path + ": " + colorString);
        }
        return Color.BLUE;
    }
    private int getValueFromConfig(ItemStack item, String valueType) {
        if (item.hasItemMeta()) {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            if (meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                String drinkType = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                return plugin.getFiles().getItemsConfig().getInt(drinkType + "." + valueType, 0);
            }
        }
        return 0;
    }
    
    public int getCost(ItemStack item) {
        return getValueFromConfig(item, "shop_cost");
    }
    
    public int thirstValue(ItemStack item) {
        return getValueFromConfig(item, "thirst_value");
    }
    
    public int fatigueValue(ItemStack item) {
        return getValueFromConfig(item, "fatigue_value");
    }
    public boolean isDrink(ItemStack item) {
        if (item.hasItemMeta()){
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            return meta.getPersistentDataContainer().has(key, PersistentDataType.STRING);
        }
        return false;
    }
    public ItemStack getlemon() {
        ItemStack lemon = new ItemStack(Material.LIME_DYE);
        ItemMeta meta = lemon.getItemMeta();
        String name = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("others.lemon_item_name", "Lemon"));
        meta.setDisplayName(name);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "lemon");
        lemon.setItemMeta(meta);

        return lemon;
    }
    public ItemStack getFromString(String s) {
        try {
            DrinkType type = DrinkType.valueOf(s.toUpperCase());
            return getDrink(type);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().info("Invalid drink type: " + s);
            return null;
        }
    }
}
