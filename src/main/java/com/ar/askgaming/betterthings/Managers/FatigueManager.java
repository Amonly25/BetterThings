package com.ar.askgaming.betterthings.Managers;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;

public class FatigueManager extends BukkitRunnable {

    private int increaseAmount = 4;
    private int maxFatigue = 250;

    private BetterThings plugin;
    public FatigueManager(BetterThings main) {
        plugin = main;

        for (Player p : plugin.getServer().getOnlinePlayers()) {

			add(p);
		}
    }

    private HashMap<Player, Integer> fatigueMap = new HashMap<>();

    public HashMap<Player, Integer> getMap() {
        return fatigueMap;
    }

    @Override
    public void run() {
        for (Player p : getMap().keySet()) {
            increase(p, increaseAmount);
        }
    }
    public int getCurrent(Player p){
        return getMap().get(p);
    }

    public void setFatigue(Player p, int amount){
        getMap().put(p, amount);
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        data.set(p.getName()+".fatigue.amount", amount);
    }

    public void increase(Player p, int amount){
        if (getCurrent(p) >= maxFatigue) {
            return;
        }

        if (getCurrent(p) + amount > maxFatigue) {
            setFatigue(p, maxFatigue);
        } else {
            //swtich?
            setFatigue(p, getCurrent(p) + amount);
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
}
