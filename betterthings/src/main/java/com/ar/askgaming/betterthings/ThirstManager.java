package com.ar.askgaming.betterthings;

import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ThirstManager extends BukkitRunnable{

    private BetterThings plugin;

    public ThirstManager(BetterThings betterThings) {
        plugin = betterThings;

        for (Player p : plugin.getServer().getOnlinePlayers()) {
			//Check if has enabled the Thirst and Fatigue

			FileConfiguration data = plugin.getFiles().getPlayerDataConfig();
			String name = p.getName();

			if (data.getBoolean(name+".thirst.enabled",true)) {
				int thirstAmount = data.getInt(name+".thirst.amount",0);
				get().put(p, thirstAmount);
			}
		}
    }

    private HashMap<Player, Integer> thirst = new HashMap<>();

    public HashMap<Player, Integer> get() {
        return thirst;
    }

    @Override
    public void run() {
        for (Player p : get().keySet()) {

            int thirst = get().get(p);

            if (thirst > 0) {
                
            } 
        } 
    }
}
