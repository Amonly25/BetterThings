package com.ar.askgaming.betterthings.Drinks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Drinks.Items.DrinkType;

public class BarShop {

    private BetterThings plugin;
    private Inventory inv;
    
    public Inventory getInv() {
        return inv;
    }

    public BarShop(BetterThings main) {
        plugin = main;

        inv = Bukkit.createInventory(null, 18,"Bar Shop");
        initializeItems();

    }
    private void initializeItems() {
    				
        Items i = plugin.getItems();

        inv.setItem(0, getItemLored(i.getDrink(DrinkType.WATER), "WATER"));
    	inv.setItem(1, getItemLored(i.getDrink(DrinkType.COFFEE), "COFFEE"));
        inv.setItem(2, getItemLored(i.getDrink(DrinkType.JUICE), "JUICE"));
        inv.setItem(3, getItemLored(i.getDrink(DrinkType.LEMONADE), "LEMONADE"));
        inv.setItem(4, getItemLored(i.getDrink(DrinkType.WINE), "WINE"));
        inv.setItem(5, getItemLored(i.getDrink(DrinkType.GOD_JUICE), "GOD_JUICE"));
      
    }
    private ItemStack getItemLored(ItemStack i, String type) {
    	
    	ItemMeta im = i.getItemMeta();
    	List<String> lore;
    	
    	if (im.getLore() == null) {
        	lore = new ArrayList<>();
        	
        	//lore.add("");
        	//lore.add(Main.files.getLang("cost").replace("%cost%", Main.files.getItems().getString(type+ ".shop_cost")).replace("&", "�"));
    	} else {
        	lore = im.getLore();
        	
        	//lore.add("");
        	//lore.add(Main.files.getLang("cost").replace("%cost%", Main.files.getItems().getString(type+ ".shop_cost")).replace("&", "�"));
    	}
    	
    	im.setLore(lore);
    	i.setItemMeta(im);
   	
    	return i;
    	
    }

}
