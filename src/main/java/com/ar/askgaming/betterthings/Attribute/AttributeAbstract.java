package com.ar.askgaming.betterthings.Attribute;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.GameMode;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Controllers.FilesManager;
import com.ar.askgaming.betterthings.Controllers.AttributeManager.AttributeType;
import com.ar.askgaming.betterthings.Events.AttributeChangeEvent;

public abstract class AttributeAbstract {

    protected final AttributeType type;
    protected final String configKey;
    protected final FilesManager data;

    protected HashMap <Player,Integer> lastValue;
    protected double decreaseAmount;
    protected double maxAttribute;
    protected List<String> enabledWorlds;
    protected String emoji;

    protected final BetterThings plugin;

    public AttributeAbstract(AttributeType type) {
        this.type = type;
        this.configKey = type.toString().toLowerCase();
        lastValue = new HashMap<>();

        plugin = BetterThings.getInstance();
        data = plugin.getFiles();
        loadData();
    }

    public void loadData() {
        maxAttribute = plugin.getConfig().getDouble(configKey + ".max_value",20);
        decreaseAmount = plugin.getConfig().getDouble(configKey + ".decrease",0.25);
        enabledWorlds = plugin.getConfig().getStringList(configKey + ".enable_worlds");
        emoji = plugin.getConfig().getString(configKey + ".emoji");
    }

    public Double getAttribute(Player p) {
        return data.getPlayerDataConfig().getDouble(p.getName() + "." + configKey + ".amount", maxAttribute);
    }
    public boolean hasEnabled(Player p) {

        if (!enabledWorlds.contains(p.getWorld().getName()) || !p.getGameMode().equals(GameMode.SURVIVAL)) {
            return false;
        }
        return data.getPlayerDataConfig().getBoolean(p.getName() + "." + configKey + ".enabled", true);
    }
    public void setAttribute(Player p, Double amount) {
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        data.set(p.getName() + "." + configKey + ".amount", amount);

        plugin.getActionBar().sendMessage(p);
        AttributeChangeEvent event = new AttributeChangeEvent(type, amount, p);
        plugin.getServer().getPluginManager().callEvent(event);
        // 
        // plugin.getLogger().info("AttributeChangeEvent called for " + p.getName() + " with " + type + " and " + amount);
        checkValues(p);
    }

    public void increase(Player p, Double amount) {
        double newAttribute = Math.min(getAttribute(p) + amount, maxAttribute);
        setAttribute(p, newAttribute);
    }

    public void decrease(Player p, Double amount) {
        if (amount == null) {
            amount = decreaseAmount;
        }
        double newAttribute = Math.max(getAttribute(p) - amount, 0);
        setAttribute(p, newAttribute);
    }
    public void toggle(Player p) {
        String name = p.getName();
        boolean enabled = !data.getPlayerDataConfig().getBoolean(p.getName() + "." + configKey + ".enabled", true);
        data.getPlayerDataConfig().set(name + "." + configKey + ".enabled", enabled);
        p.sendMessage(data.getLang(configKey + ".toggle").replace("%state%", String.valueOf(enabled)));
    }
    
    private void checkValues(Player p) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection(configKey + ".values");
        if (section == null) {
            return;
        }
        Set<String> keys = section.getKeys(false);
        if (keys.isEmpty()) {
            return;
        }
        for (String key : keys) {
            int value = Integer.parseInt(key);

            if (Math.round(getAttribute(p)) == value) {
                if (lastValue.containsKey(p)) {
                    if (lastValue.get(p) == value) {
                        return;
                    }
                }
                lastValue.put(p, value);
                plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        lastValue.remove(p);
                    }
                }, 20*30L);
                plugin.getAttributeManager().processValue(p, configKey, value);
                break;
            }
        }
    }
    
    public String getPercent(Player p) {
        return String.valueOf((int) Math.round((double) getAttribute(p) / maxAttribute * 100));
    }
    public Double getDecreaseAmount() {
        return decreaseAmount;
    }

    public Double getMaxAttribute() {
        return maxAttribute;
    }

    public List<String> getEnabledWorlds() {
        return enabledWorlds;
    }
    public String getEmoji() {
        return emoji;
    }
    public String getConfigKey() {
        return configKey;
    }
}
