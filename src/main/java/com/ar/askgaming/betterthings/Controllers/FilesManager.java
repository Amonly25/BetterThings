package com.ar.askgaming.betterthings.Controllers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ar.askgaming.betterthings.BetterThings;

import net.md_5.bungee.api.ChatColor;

public class FilesManager{
    
    private BetterThings plugin;

    private File playerData, langFile, itemsFile;
    private FileConfiguration playerDataConfig, langConfig, itemsConfig;

    public FilesManager(BetterThings main) {
        plugin = main;

        playerData = createFile("playerdata.yml");
        itemsFile = createFile("drinks.yml");
        langFile = createFile("lang.yml");

        reloadFiles();
    }
    public void reloadFiles(){
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
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
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
        return ChatColor.translateAlternateColorCodes('&', langConfig.getString(path, "Undefined path: " +path));
    }
    public FileConfiguration getItemsConfig() {
        return itemsConfig;
    }

    public FileConfiguration getPlayerDataConfig() {
        return playerDataConfig;
    }
}
