package com.ar.askgaming.betterthings.Managers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Listeners.PlayerMoveListener;

public abstract class AttributeManager extends BukkitRunnable{

    protected BetterThings plugin;
    protected int decreaseAmount;
    protected int maxAttribute;
    protected List<String> enabledWorlds;
    protected final Map<Player, Block> lastBlockStanding = new HashMap<>();
    protected final Map<Player, Integer> attributeMap = new HashMap<>();

    public AttributeManager(BetterThings plugin, int maxAttribute, String configKey) {
        this.plugin = plugin;
        this.maxAttribute = maxAttribute;
        this.decreaseAmount = plugin.getConfig().getInt(configKey + ".decrease");
        this.enabledWorlds = plugin.getConfig().getStringList(configKey + ".enable_worlds");
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            add(p);
        }
    }

    public boolean isInEnabledWorld(Player p) {
        return enabledWorlds.contains(p.getWorld().getName());
    }

    public Map<Player, Integer> getMap() {
        return attributeMap;
    }

    @Override
    public void run() {
        for (Player p : getMap().keySet()) {
            if (lastBlockStanding.containsKey(p)){
                if (lastBlockStanding.get(p).equals(PlayerMoveListener.lastBlockStanding.get(p))) {
                    return;
                }
            }
            lastBlockStanding.put(p, p.getLocation().getBlock());

            if (isInEnabledWorld(p)) {
                decrease(p, decreaseAmount);
                handleLowAttribute(p);
            }
        }
    }

    public int getCurrent(Player p) {
        return attributeMap.getOrDefault(p, maxAttribute);
    }

    public void setAttribute(Player p, int amount) {
        attributeMap.put(p, amount);
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        data.set(p.getName() + getConfigKey() + ".amount", amount);
        plugin.getActionBar().sendMessage(p);
    }

    public void increase(Player p, int amount) {
        int newAttribute = Math.min(getCurrent(p) + amount, maxAttribute);
        setAttribute(p, newAttribute);
    }

    public void decrease(Player p, int amount) {
        int newAttribute = Math.max(getCurrent(p) - amount, 0);
        setAttribute(p, newAttribute);
    }

    public void toggle(Player p) {
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        String name = p.getName();
        p.sendMessage(plugin.getFiles().getLang(getConfigKey() + ".toggle").replace("%state%", String.valueOf(!hasEnabled(p))));
        data.set(name + "." + getConfigKey() + ".enabled", !hasEnabled(p));

        if (hasEnabled(p)) {
            attributeMap.remove(p);
        } else {
            add(p);
        }
    }

    public boolean hasEnabled(Player p) {
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        String name = p.getName();
        return data.getBoolean(name + "." + getConfigKey() + ".enabled", true);
    }

    public void add(Player p) {
        if (!attributeMap.containsKey(p) && hasEnabled(p)) {
            int amount = plugin.getFiles().getPlayerDataConfig().getInt(p.getName() + "." + getConfigKey() + ".amount", 0);
            attributeMap.put(p, amount);
        }
    }

    public String getPercent(Player p) {
        return String.valueOf((int) Math.round((double) getCurrent(p) / maxAttribute * 100));
    }

    protected abstract void handleLowAttribute(Player p);

    protected abstract String getConfigKey();
}
