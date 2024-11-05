package com.ar.askgaming.betterthings.Managers;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Events.IncreaseFatigueEvent;

import net.md_5.bungee.api.ChatColor;

public class FatigueManager extends BukkitRunnable {

    private int increaseAmount = 4;
    private int maxFatigue = 250;
    private List<String> enabledWorlds;

    private BetterThings plugin;
    public FatigueManager(BetterThings main) {
        plugin = main;
        increaseAmount = plugin.getConfig().getInt("fatigue.increase");
        enabledWorlds = plugin.getConfig().getStringList("fatigue.enable_worlds");

        for (Player p : plugin.getServer().getOnlinePlayers()) {
			add(p);
		}
    }
    public boolean isInEnabledWorld(Player p) {
        return enabledWorlds.contains(p.getWorld().getName());
    }

    private HashMap<Player, Integer> fatigueMap = new HashMap<>();

    public HashMap<Player, Integer> getMap() {
        return fatigueMap;
    }

    @Override
    public void run() {
        for (Player p : getMap().keySet()) {
            if (isInEnabledWorld(p)) {
                decrease(p, increaseAmount);
            }
        }
    }
    public int getCurrent(Player p){
        if (getMap().containsKey(p)) {
            return getMap().get(p);
        } else return maxFatigue;
    }

    public void setFatigue(Player p, int amount){

        getMap().put(p, amount);

        IncreaseFatigueEvent e = new IncreaseFatigueEvent(p, amount, plugin.getActionBar().getEmojiMessage(p));
        Bukkit.getPluginManager().callEvent(e);

        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        data.set(p.getName()+".fatigue.amount", amount);
    }

    public void increase(Player p, int amount){
        if (getCurrent(p) >= maxFatigue) {
            setFatigue(p, getCurrent(p));
        }

        if (getCurrent(p) + amount > maxFatigue) {
            setFatigue(p, maxFatigue);
        } else {
            //swtich?
            setFatigue(p, getCurrent(p) + amount);
        }
    }
    public void decrease(Player p, int amount){
        if (getCurrent(p) == 0) {
            setFatigue(p, getCurrent(p));
        }
        if (getCurrent(p) - amount < 0) {
            setFatigue(p, 0);
        } else {
            setFatigue(p, getCurrent(p) - amount);
        }
    }
    
    public void toggle(Player p){
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        String name = p.getName();
        p.sendMessage(plugin.getFiles().getLang("fatigue.toggle").replace("%state%", String.valueOf(!hasEnabled(p))));

        if (hasEnabled(p)) {
            data.set(name+".fatigue.enabled", false);
            getMap().remove(p);
        } else {
            add(p);
            data.set(name+".fatigue.enabled", true);
        }
    }

    public boolean hasEnabled(Player p){
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        String name = p.getName();
        return data.getBoolean(name+".fatigue.enabled",true);
    }
    public void add(Player p) {
        if (getMap().containsKey(p)) {
            return;
        }
        if (hasEnabled(p)) {
            int i = plugin.getFiles().getPlayerDataConfig().getInt(p.getName()+".fatigue.amount",0);
            getMap().put(p, i);
        }
    }
    public String getMessage(Player p) {
        String str = plugin.getConfig().getString("message.fatigue_change").replace("%fatigue_amount%", String.valueOf(getCurrent(p)))
				.replace("%thirst_amount%", String.valueOf(plugin.getThirstManager().getCurrent(p)));

        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public String getPercent(Player p) {
        return String.valueOf((int) Math.round((double) getCurrent(p) / maxFatigue * 100));
    }
}
