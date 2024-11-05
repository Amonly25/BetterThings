package com.ar.askgaming.betterthings;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.betterthings.Drinks.BarShop;
import com.ar.askgaming.betterthings.Drinks.Items;
import com.ar.askgaming.betterthings.Listeners.InventoryClickListener;
import com.ar.askgaming.betterthings.Listeners.ItemConsumeListener;
import com.ar.askgaming.betterthings.Listeners.PlayerJoinListener;
import com.ar.askgaming.betterthings.Listeners.PlayerQuitListener;
import com.ar.askgaming.betterthings.Managers.ActionBarTask;
import com.ar.askgaming.betterthings.Managers.FatigueManager;
import com.ar.askgaming.betterthings.Managers.FilesManager;
import com.ar.askgaming.betterthings.Managers.ThirstManager;

import me.clip.placeholderapi.libs.kyori.adventure.text.event.HoverEvent.Action;

public class BetterThings extends JavaPlugin{

	private ThirstManager tManager;
	private FatigueManager fManager;
	private FilesManager files;
	private ActionBarTask actionBar;
	private Items items;
	private BarShop barShop;

	public void onEnable() {
		
		saveDefaultConfig();
		
		loadManagers();

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		getServer().getPluginManager().registerEvents(new ItemConsumeListener(this), this);
		getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);

		getServer().getPluginCommand("thirst").setExecutor(new Commands(this));
		getServer().getPluginCommand("fatigue").setExecutor(new Commands(this));
		getServer().getPluginCommand("bar").setExecutor(new Commands(this));
		getServer().getPluginCommand("betterthings").setExecutor(new Commands(this));

		ActionBarTask actionBar = new ActionBarTask(this);
		actionBar.runTaskTimer(this, 0, 20);

		items = new Items(this);
		barShop = new BarShop(this);

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
		actionBar.runTaskTimer(this, 60, 20);

		if (fManager != null) {
			fManager.cancel();
		}
		if (getConfig().getBoolean("fatigue.enable",true)){
			fManager = new FatigueManager(this);
			fManager.runTaskTimer(this, 0, 1200);
		} else getLogger().log(Level.INFO, "Fatigue feature disabled.");

		if (tManager != null) {
			tManager.cancel();
		}
		if (getConfig().getBoolean("thirst.enable",true)){
			tManager = new ThirstManager(this);
			tManager.runTaskTimer(this, 0, 1200);
		} else getLogger().log(Level.INFO, "Thirst feature disabled.");
	}
	public void onDisable(){
		files.savePlayerData();
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
}


