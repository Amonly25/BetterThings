package com.ar.askgaming.betterthings;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

public class FilesManager extends BukkitRunnable{
    
    private BetterThings plugin;

    private File playerData;
    private FileConfiguration playerDataConfig;

    public FileConfiguration getPlayerDataConfig() {
        return playerDataConfig;
    }

    public FilesManager(BetterThings main) {
        plugin = main;

        playerData = new File(plugin.getDataFolder(), "playerData.yml");
        if (!playerData.exists()) {
            plugin.saveResource("playerData.yml", false);
        }
        playerDataConfig = new YamlConfiguration();

        try {
            playerDataConfig.load(playerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void save(){
        try {
            playerDataConfig.save(playerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        save();
    }
}
