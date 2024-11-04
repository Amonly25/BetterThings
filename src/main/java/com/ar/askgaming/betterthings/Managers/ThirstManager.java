package com.ar.askgaming.betterthings.Managers;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;

public class ThirstManager extends BukkitRunnable{

    private int increaseAmount = 4;
    private int maxThirst = 200;

    private BetterThings plugin;

    public ThirstManager(BetterThings betterThings) {
        plugin = betterThings;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
			add(p);
		}
    }
    
    private HashMap<Player, Integer> thirstMap = new HashMap<>();

    public HashMap<Player, Integer> getMap() {
        return thirstMap;
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

    public void setThirst(Player p, int amount){
        getMap().put(p, amount);
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        data.set(p.getName()+".thirst.amount", amount);
    }

    public void increase(Player p, int amount){
        if (getCurrent(p) >= maxThirst) {
            return;
        }
        if (getCurrent(p) + amount > maxThirst) {
            setThirst(p, maxThirst);
        } else {
            setThirst(p, getCurrent(p) + amount);
        }
    }

    public boolean hasEnabled(Player p){
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        String name = p.getName();
        return data.getBoolean(name+".thirst.enabled",true);
    }
    public void add(Player p) {
        if (getMap().containsKey(p)) {
            return;
        }
        if (hasEnabled(p)) {
            int i = plugin.getFiles().getPlayerDataConfig().getInt(p.getName()+".thirst.amount",0);
            getMap().put(p, i);
        }
    }
}
