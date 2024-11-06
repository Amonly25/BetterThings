package com.ar.askgaming.betterthings.Managers;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;

import net.md_5.bungee.api.ChatColor;

public class FilesManager extends BukkitRunnable{
    
    private BetterThings plugin;

    private File playerData, langFile, itemsFile;
    private FileConfiguration playerDataConfig, langConfig, itemsConfig;

    public FilesManager(BetterThings main) {
        plugin = main;

        playerData = createFile("playerdata.yml");
        itemsFile = createFile("drinks.yml");
        langFile = createFile("lang.yml");

        playerDataConfig = loadConfiguration(playerData);
        itemsConfig = loadConfiguration(itemsFile);
        langConfig = loadConfiguration(langFile);
    }

    private File createFile(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()){
            plugin.saveResource(fileName, false);
        }
        return file;
    }

    private FileConfiguration loadConfiguration(File file) {
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load configuration from file: " + file.getName(), e);
        }
        return config;
    }
    public void savePlayerData(){
        try {
            playerDataConfig.save(playerData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLang(String path){
        return ChatColor.translateAlternateColorCodes('&', langConfig.getString(path,"Unknown"));
    }
    public FileConfiguration getItemsConfig() {
        return itemsConfig;
    }

    public FileConfiguration getPlayerDataConfig() {
        return playerDataConfig;
    }

    @Override
    public void run() {
        savePlayerData();
    }
}
