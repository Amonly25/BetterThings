package betterthings.events;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import betterthings.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SleepEvent implements Listener {

	static Main plugin;
	public SleepEvent(Main main) {
		plugin = main;
	}

    @EventHandler()
    public void onleave(PlayerBedLeaveEvent e) {
    	Player p = (Player) e.getPlayer();
    	
    	plugin.getConfig().set("players.fatigue." + p.getUniqueId(), 0);
    	double randDoublet = Math.random();
		if (randDoublet <= 0.30D) {	
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.aftersleep")));
			p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 300, 0));
		}
    }
    
    @EventHandler()
    public void onPlayerInteract(PlayerInteractEvent e) {
    	
    	Player p = (Player) e.getPlayer();
    	int currentfatigue = plugin.getConfig().getInt("Players.fatigue." + p.getUniqueId());
    	int currentthirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
    	List<String> materials = plugin.getConfig().getStringList("materials");
    	
    	if (e.getAction() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	    	if (currentfatigue >= 50) {
	    		for (String list : materials) {
					if (e.getClickedBlock().getType() == Material.valueOf(list)) {
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
									("§7Sed: §f" + currentthirst + " §b|||| §7- Cansancio: §f" + currentfatigue + " §b||||"));
									plugin.getConfig().set("players.fatigue." + p.getUniqueId(), (currentfatigue - 50));
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.restinday")));
							p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0));
			        	}
		        	}
	    		}
	    	}		
    	}
    
}
