package com.ar.askgaming.betterthings.Drinks;

import java.util.jar.Attributes.Name;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Drinks.Items.DrinkType;

import net.md_5.bungee.api.ChatColor;

public class Recipes {
    
    private BetterThings plugin;

    public Recipes(BetterThings main) {
        plugin = main;

        if (plugin.getConfig().getBoolean("others.enable_recipes")) {

            Items items = plugin.getItems();

            addRecipeIfNotExists("juice", items.getDrink(DrinkType.JUICE), "AAA", " G ", "   ", 
                                'A', Material.APPLE, 'G', Material.GLASS_BOTTLE);

            addRecipeIfNotExists("godjuice", items.getDrink(DrinkType.GOD_JUICE), "AAA", " G ", "   ", 
                                'A', Material.GOLDEN_APPLE, 'G', Material.GLASS_BOTTLE);

            addRecipeIfNotExists("lemonade", items.getDrink(DrinkType.LEMONADE), "LLL", " G ", "   ", 
                                'L', Material.LIME_DYE, 'G', Material.GLASS_BOTTLE);
        }
    }

    private void addRecipeIfNotExists(String key, ItemStack result,  String row1, String row2, String row3, char ingredient1Char, Material ingredient1Material,  char ingredient2Char, Material ingredient2Material) {
       
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        
        if (Bukkit.getRecipe(namespacedKey) == null) {
            ShapedRecipe recipe = new ShapedRecipe(namespacedKey, result);
            recipe.shape(row1, row2, row3);
            recipe.setIngredient(ingredient1Char, ingredient1Material);
            recipe.setIngredient(ingredient2Char, ingredient2Material);
            Bukkit.addRecipe(recipe);
        }
    }
}
