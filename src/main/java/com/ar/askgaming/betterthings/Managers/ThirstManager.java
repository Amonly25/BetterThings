package com.ar.askgaming.betterthings.Managers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Events.IncreaseThirstEvent;

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
           decrease(p, increaseAmount);
        } 
    }

    public int getCurrent(Player p){
        if (getMap().containsKey(p)) {
            return getMap().get(p);
        } else return maxThirst;
    }

    public void setThirst(Player p, int amount){

        getMap().put(p, amount);

        IncreaseThirstEvent e = new IncreaseThirstEvent(p, amount, plugin.getActionBar().getEmojiMessage(p));
        Bukkit.getPluginManager().callEvent(e);

        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        data.set(p.getName()+".thirst.amount", amount);
    }

    public void increase(Player p, int amount){
        if (getCurrent(p) >= maxThirst) {
            setThirst(p, getCurrent(p));
        }
        if (getCurrent(p) + amount > maxThirst) {
            setThirst(p, maxThirst);
        } else {
            setThirst(p, getCurrent(p) + amount);
        }
    }
    public void decrease(Player p, int amount){
        if (getCurrent(p) == 0) {
            setThirst(p, getCurrent(p));
        }
        if (getCurrent(p) - amount < 0) {
            setThirst(p, 0);
        } else {
            setThirst(p, getCurrent(p) - amount);
        }
    }

    public void toggle(Player p){
        FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
        String name = p.getName();
        p.sendMessage(plugin.getFiles().getLang("thirst.toggle").replace("%state%", String.valueOf(!hasEnabled(p))));
        if (hasEnabled(p)) {
            data.set(name+".thirst.enabled", false);
            getMap().remove(p);
        } else {
            data.set(name+".thirst.enabled", true);
            add(p);
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

    public String getPercent(Player p) {
        return String.valueOf((int) Math.round((double) getCurrent(p) / 200 * 100));
    }

}
