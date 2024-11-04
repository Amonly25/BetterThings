package betterthings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ThirstTask implements Runnable {

	static Main plugin;
	public ThirstTask(Main main) {
		plugin = main;
	}
	
	
	@Override
	public void run() {

		for (Player pl : Bukkit.getOnlinePlayers()) {
    			if (pl.getGameMode() == GameMode.SURVIVAL) {
    				
    				int currentthirst = plugin.getConfig().getInt("players.thirst." + pl.getUniqueId());
    				int currentfatigue = plugin.getConfig().getInt("players.fatigue." + pl.getUniqueId());
    				int howmuchthirst = plugin.getConfig().getInt("thirst.howmuch");
    				   				
    				if (currentthirst < 200) {
    					
    					plugin.getConfig().set("players.thirst." + pl.getUniqueId(), (currentthirst + howmuchthirst));   					
    					pl.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
    							("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));
    				}
    				if (currentthirst >= 150 ) {
       					
    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("thirst.150.msg")));
						pl.damage(4.0);
						pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
						pl.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
    				}
    				if (currentthirst >= 100 && currentthirst <=149) {
    					
    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("thirst.100.msg")));;
						pl.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 1));
    				}
    				
    				if (currentthirst >= 50 && currentthirst <=99) {
    					
    					pl.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("thirst.50.msg")));
    				}
    			}
    		}
    	}	
}
