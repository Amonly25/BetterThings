package betterthings.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import betterthings.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class BlockEvent implements Listener {

	static Main plugin;
	public BlockEvent(Main main) {
		plugin = main;
	}
	
    @EventHandler()
    public void onPlace(BlockPlaceEvent e) {
    	
    	Player p = (Player) e.getPlayer();
    	double randDoublet = Math.random();
    	int currentfatigue = plugin.getConfig().getInt("players.fatigue." + p.getUniqueId());
    	int currentthirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
    	
    	String fatiga = ("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||");
    	
			if (currentfatigue >= 180 ) {
					
				if (randDoublet <= 0.30D) {	
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tootired")));
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(fatiga));
					e.setCancelled(true);				
			}
		}   	
    }
    @EventHandler()
    public void onBreak(BlockBreakEvent e) {
    
    	Player p = (Player) e.getPlayer();
    	double randDoublet = Math.random();
    	int Currentfatiga = plugin.getConfig().getInt("players.fatigue." + p.getUniqueId());
    	int CurrentThirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
    	String fatiga = ("§7Thirst: §f" + CurrentThirst + " §b|||| §7- Fatigue: §f" + Currentfatiga + " §b||||");
    	
			if (Currentfatiga >= 180 ) {
							
				if (randDoublet <= 0.30D) {	
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tootired")));
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(fatiga));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 200, 1));
			}
		}   	
    }
}
