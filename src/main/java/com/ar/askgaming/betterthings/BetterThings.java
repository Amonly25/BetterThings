package com.ar.askgaming.betterthings;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.betterthings.Drinks.BarShop;
import com.ar.askgaming.betterthings.Drinks.Items;
import com.ar.askgaming.betterthings.Drinks.Recipes;
import com.ar.askgaming.betterthings.Integrations.PlaceHolders;
import com.ar.askgaming.betterthings.Listeners.DecayListener;
import com.ar.askgaming.betterthings.Listeners.InventoryClickListener;
import com.ar.askgaming.betterthings.Listeners.PlaceBreakBlockListener;
import com.ar.askgaming.betterthings.Listeners.PrepareItemCraftListener;
import com.ar.askgaming.betterthings.Listeners.RegainHealthListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.ItemConsumeListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerBedListeners;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerDeathListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerInteractListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerJoinListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerMoveListener;
import com.ar.askgaming.betterthings.Listeners.PlayerListeners.PlayerQuitListener;
import com.ar.askgaming.betterthings.Managers.ActionBarTask;
import com.ar.askgaming.betterthings.Managers.FatigueManager;
import com.ar.askgaming.betterthings.Managers.FilesManager;
import com.ar.askgaming.betterthings.Managers.ThirstManager;

import net.milkbowl.vault.economy.Economy;


public class BetterThings extends JavaPlugin{

	//Owns classes
	private ThirstManager tManager;
	private FatigueManager fManager;
	private FilesManager files;
	private ActionBarTask actionBar;
	private Items items;
	private BarShop barShop;
	private Recipes recipes;

	//Hooks
	private Economy vaultEconomy;

	public void onEnable() {
		
		saveDefaultConfig();
		
		loadManagers();

		items = new Items(this);
		barShop = new BarShop(this);
		recipes = new Recipes(this);
		
		registerListeners();

		getServer().getPluginCommand("thirst").setExecutor(new Commands(this));
		getServer().getPluginCommand("fatigue").setExecutor(new Commands(this));
		getServer().getPluginCommand("bar").setExecutor(new Commands(this));
		getServer().getPluginCommand("betterthings").setExecutor(new Commands(this));

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

	public void loadManagers(){
		if (files  != null) {
			files.cancel();
		}
		files = new FilesManager(this);
		files.runTaskTimer(this, 600, 1200);

		if (actionBar != null) {
			actionBar.cancel();
		}
		actionBar = new ActionBarTask(this);
		actionBar.runTaskTimer(this, 60, 40);

		if (fManager != null) {
			fManager.cancel();
		}
		fManager = new FatigueManager(this);
		fManager.runTaskTimer(this, 0, 1200);

		if (tManager != null) {
			tManager.cancel();
		}
		tManager = new ThirstManager(this);
		tManager.runTaskTimer(this, 600, 1200);
	}

	private void registerListeners(){

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new ItemConsumeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
		getServer().getPluginManager().registerEvents(new RegainHealthListener(this), this);
		getServer().getPluginManager().registerEvents(new PlaceBreakBlockListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerBedListeners(this), this);
		getServer().getPluginManager().registerEvents(new PrepareItemCraftListener(this), this);
		getServer().getPluginManager().registerEvents(new DecayListener(this), this);

		new PlayerInteractListener(this);
	}
	
	public ThirstManager getThirstManager() {
		return tManager;
	}
	public FilesManager getFiles() {
		return files;
	}
	public FatigueManager getFatigueManager() {
		return fManager;
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
}


