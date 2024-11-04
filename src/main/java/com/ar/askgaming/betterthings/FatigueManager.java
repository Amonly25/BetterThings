package com.ar.askgaming.betterthings;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FatigueManager extends BukkitRunnable {

    private BetterThings plugin;
    public FatigueManager(BetterThings main) {
        plugin = main;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
			//Check if has enabled the Thirst and Fatigue

			FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
			String name = p.getName();

			if (data.getBoolean(name+".fatigue.enabled",true)) {
				int amount = data.getInt(name+".fatigue.amount",0);
				get().put(p, amount);
			}
		}
    }

    private HashMap<Player, Integer> fatigue = new HashMap<>();

    public HashMap<Player, Integer> get() {
        return fatigue;
    }

    @Override
    public void run() {
        for (Player p : get().keySet()) {

            int fatigue = get().get(p);

            if (fatigue > 0) {

            } 
        }
    }
}
