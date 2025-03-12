package com.ar.askgaming.betterthings.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;

public class AttributeManager{

    private final BetterThings plugin;
    private final Fatigue fatigue;
    private final Thirst thirst;

    public enum AttributeType {
        THIRST, FATIGUE
    }

    protected final Map<Player, Block> lastBlockStanding = new HashMap<>();

    public AttributeManager(BetterThings plugin) {
        this.plugin = plugin;
        fatigue = new Fatigue();
        thirst = new Thirst();
        new AttributeTask(this);


    }
    public void reload(){
        thirst.loadData();
        fatigue.loadData();
    }

    public Fatigue getFatigue() {
        return fatigue;
    }
    public Thirst getThirst() {
        return thirst;
    }

    public void processValue(Player p, String configKey, int value) {

        FileConfiguration config = plugin.getConfig();
        String message = config.getString(configKey + ".values." + value + ".message");
        if (message != null) {
            p.sendMessage(message.replace('&', '§'));
        }
        Double damage = config.getDouble(configKey + ".values." + value + ".damage");
        if (damage > 0) {
            if (p.getHealth() - damage <= 0) {
            } else {
                p.damage(damage);
            }
        }
        sendCommandIfExist(configKey, value, p);
        addPotionEffect(configKey, value, p);
        playSoundIfExist(configKey, value, p);

    }
    private void sendCommandIfExist(String configKey, int value, Player player) {
        FileConfiguration config = plugin.getConfig();
        String command = config.getString(configKey + ".values." + value + ".commands");
        if (command != null) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
        }
    }
    private void addPotionEffect(String configKey, int value, Player player) {
        FileConfiguration config = plugin.getConfig();
        List<String> effects = config.getStringList(configKey + ".values." + value + ".effects");
        if (effects != null) {
            for (String effect : effects) {
                String[] split = effect.split(":");
                try {
                    int duration = Integer.parseInt(split[1]);
                    int amplifier = Integer.parseInt(split[2]);
                    PotionEffectType type = org.bukkit.Registry.EFFECT.match(split[0].toUpperCase()); // Check if the effect is in the

                    player.addPotionEffect(new PotionEffect(type, duration, amplifier));
                } catch (Exception e) {
                    plugin.getLogger().warning("Wrong potion effect format: " + effect);
                    e.printStackTrace();
                }
            }
        }
    }
    private void playSoundIfExist(String configKey, int value, Player player) {
        FileConfiguration config = plugin.getConfig();
        List<String> sound = config.getStringList(configKey + ".values." + value + ".sounds");
        if (sound != null) {
            for (String s : sound) {
                try {
                    player.playSound(player.getLocation(), Sound.valueOf(s.toUpperCase()), 3, 1);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Invalid sound: " + s);
                    return;
                }
            }
        }
    }
}
