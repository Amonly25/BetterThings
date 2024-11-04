package com.ar.askgaming.betterthings;

import org.bukkit.plugin.java.JavaPlugin;

import com.ar.askgaming.betterthings.Listeners.PlayerJoinListener;
import com.ar.askgaming.betterthings.Listeners.PlayerQuitListener;
import com.ar.askgaming.betterthings.Managers.FatigueManager;
import com.ar.askgaming.betterthings.Managers.FilesManager;
import com.ar.askgaming.betterthings.Managers.ThirstManager;

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


