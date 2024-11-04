package com.ar.askgaming.betterthings;

import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.t;

import com.ar.askgaming.betterthings.listeners.PlayerJoinListener;
import com.ar.askgaming.betterthings.listeners.PlayerQuitListener;

public class BetterThings extends JavaPlugin{

	private ThirstManager tManager;
	private FatigueManager fManager;
	private FilesManager files;

	public void onEnable() {
		
		saveDefaultConfig();
		
		loadManagers();

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);

	}
	public void loadManagers(){
		if (files  != null) {
			files.cancel();
		}
		files = new FilesManager(this);
		files.runTaskTimer(this, 600, 1200);

		if (tManager != null) {
			tManager.cancel();
		}
		tManager = new ThirstManager(this);
		tManager.runTaskTimer(this, 600, 1200);

		if (fManager != null) {
			fManager.cancel();
		}
		fManager = new FatigueManager(this);
		fManager.runTaskTimer(this, 600, 1200);
	}
	public void onDisable(){
		files.save();
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
}


