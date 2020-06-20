package betterthings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class FatigueTask implements Runnable {

	static Main plugin;
	public FatigueTask(Main main) {
		plugin = main;
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub

		for (Player pl : Bukkit.getOnlinePlayers()) {
    			if (pl.getGameMode() == GameMode.SURVIVAL) {
    				
    				int currentfatigue = plugin.getConfig().getInt("players.fatigue." + pl.getUniqueId());
    				int howmuchfatigue = plugin.getConfig().getInt("fatigue.howmuch");
    				int currentthirst = plugin.getConfig().getInt("players.thirst." + pl.getUniqueId());

    				if (currentfatigue < 250) {
    					
    					plugin.getConfig().set("players.fatigue." + pl.getUniqueId(), (currentfatigue + howmuchfatigue));
    					pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
    							("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));
    				}
    				if (currentfatigue >= 200 ) {
    					
    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fatigue.200.msg")));
						pl.damage(4.0);
						pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
						pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
    				}
    				if (currentfatigue >= 150 && currentfatigue <=149) {
    					
    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fatigue.150.msg")));
						pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
    				}
    				
    				if (currentfatigue >= 100 && currentfatigue <=99) {
    					
    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("fatigue.100.msg")));
    				}
    			}
    		}
    	}	
}