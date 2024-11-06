package com.ar.askgaming.betterthings.Drinks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Drinks.Items.DrinkType;

import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class BarShop {

    private BetterThings plugin;
    private Inventory inv;
    
    public Inventory getInv() {
        return inv;
    }

    public BarShop(BetterThings main) {
        plugin = main;

        inv = Bukkit.createInventory(null, 18,plugin.getFiles().getLang("bar_shop.title"));
        initializeItems();

    }
    private void initializeItems() {
    				
        Items i = plugin.getItems();

        inv.setItem(0, i.getDrink(DrinkType.WATER));
    	inv.setItem(1, i.getDrink(DrinkType.COFFEE));
        inv.setItem(2, i.getDrink(DrinkType.JUICE));
        inv.setItem(3, i.getDrink(DrinkType.LEMONADE));
        inv.setItem(4, i.getDrink(DrinkType.WINE));
        inv.setItem(5, i.getDrink(DrinkType.GOD_JUICE));
      
    }

    public void buyToServerBank(Player p, ItemStack item, double amount) {
        EconomyResponse response = buyToVault(p, item, amount);
        if (response.transactionSuccess()) {
            plugin.getRealisticEconomy().getServerBank().deposit(amount);
        } else {
            plugin.getLogger().warning("Error: The server bank could not deposit the money");
        }
    }
    public EconomyResponse buyToVault(Player p, ItemStack item, double amount) {
        if (canBuy(p, item, amount)) {
            EconomyResponse response = plugin.getVaultEconomy().withdrawPlayer(p, amount);
            if (response.transactionSuccess()) {
                p.sendMessage(plugin.getFiles().getLang("bar_shop.buy").replace("%item%", item.getItemMeta().getDisplayName()).replace("%cost%", String.valueOf(amount)));
                p.getInventory().addItem(item);
                return response;
            }
        }
        return new EconomyResponse(0, amount, ResponseType.FAILURE, "Cant buy item");
    }

    public boolean canBuy(Player buyer, ItemStack item, double amount) {
     
        if (plugin.getVaultEconomy().getBalance(buyer) < amount) {
            buyer.sendMessage(plugin.getFiles().getLang("bar_shop.no_money"));
            return false;
        }
        if (buyer.getInventory().firstEmpty() == -1) {
            buyer.sendMessage(plugin.getFiles().getLang("bar_shop.no_space"));
            return false;
        }
        return true;
    }
}
