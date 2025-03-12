package com.ar.askgaming.betterthings;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.betterthings.Controllers.ActionBarTask;
import com.ar.askgaming.betterthings.Controllers.AttributeManager;
import com.ar.askgaming.betterthings.Controllers.FilesManager;
import com.ar.askgaming.betterthings.Drinks.BarShop;
import com.ar.askgaming.betterthings.Drinks.Items;
import com.ar.askgaming.betterthings.Drinks.Recipes;
import com.ar.askgaming.betterthings.Listeners.DecayListener;
import com.ar.askgaming.betterthings.Listeners.InventoryClickListener;
import com.ar.askgaming.betterthings.Listeners.PrepareItemCraftListener;
import com.ar.askgaming.betterthings.Listeners.RegainHealthListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.ItemConsumeListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerBedListeners;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerDeathListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerInteractListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerMoveListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerQuitListener;
import com.ar.askgaming.betterthings.Utils.PlaceHolders;

import net.milkbowl.vault.economy.Economy;

public class BetterThings extends JavaPlugin{

	//Owns classes
	private FilesManager files;
	private AttributeManager attributeManager;
	private ActionBarTask actionBar;
	private Items items;
	private BarShop barShop;
	private Recipes recipes;
	private static BetterThings instance;

	//Hooks
	private Economy vaultEconomy;

	public void onEnable() {
		
		saveDefaultConfig();
		instance = this;

		files = new FilesManager(this);
		attributeManager = new AttributeManager(this);
		actionBar = new ActionBarTask(this);
		items = new Items(this);
		barShop = new BarShop(this);
		recipes = new Recipes(this);
		
		registerListeners();

		new Commands(this);

		if (getServer().getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                getLogger().info("Non economy plugin found!, shop feature disabled.");
            } else vaultEconomy = rsp.getProvider();           
        } 

		if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new PlaceHolders(this).register();

		}
	}
	public void onDisable(){
		files.savePlayerData();
	}

	private void registerListeners(){

		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new ItemConsumeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
		getServer().getPluginManager().registerEvents(new RegainHealthListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerBedListeners(this), this);
		getServer().getPluginManager().registerEvents(new PrepareItemCraftListener(this), this);
		getServer().getPluginManager().registerEvents(new DecayListener(this), this);

		new PlayerInteractListener(this);
	}
	public AttributeManager getAttributeManager() {
		return attributeManager;
	}
	
	public FilesManager getFiles() {
		return files;
	}	
	public ActionBarTask getActionBar() {
		return actionBar;
	}
	public Items getItems() {
		return items;
	}
	public BarShop getBarShop() {
		return barShop;
	}
	public Economy getVaultEconomy() {
		return vaultEconomy;
	}

	public Recipes getRecipes() {
		return recipes;
	}
    public static BetterThings getInstance() {
        return instance;
    }
}


