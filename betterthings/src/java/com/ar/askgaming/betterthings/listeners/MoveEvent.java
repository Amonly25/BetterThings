package betterthings.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import betterthings.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;


public class MoveEvent implements Listener {

	static Main plugin;
	public MoveEvent(Main main) {
		plugin = main;
	}
	
	@EventHandler
	public void PlayerMove(PlayerMoveEvent e){
		
		Player p = (Player) e.getPlayer();
		int currentthirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
		int currentfatigue = plugin.getConfig().getInt("players.fatigue." + p.getUniqueId());
		
		if (p.getLocation().getBlock().getType() == Material.TORCH ) {
			
			p.setFireTicks(20);
			if (currentthirst < 250) {
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
					("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));
					plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst + 1));
			}
		}
		if (p.getLocation().getBlock().getType() == Material.LAVA ) {
			
			if (currentthirst < 250) {
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
					("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" +currentfatigue + " §b||||"));
					plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst + 1));
			}
		}
		if (currentthirst < 250 && currentthirst > 200) {
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
					("§7Thirst: §f" + currentthirst + " §c|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));		
		}
		if (currentfatigue < 250 && currentfatigue > 200) {
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
					("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" + currentfatigue + " §c||||"));		
		}
	}
	@EventHandler
	public void PlayerSprint(PlayerToggleSprintEvent e){
		Player p = (Player) e.getPlayer();
		int currentfatigue = plugin.getConfig().getInt("players.fatigue." + p.getUniqueId());
		int currentthirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
		double randDoublet = Math.random();
		
		if (currentfatigue < 250) {
			plugin.getConfig().set("players.fatigue." + p.getUniqueId(), (currentfatigue + 1));
			
		}
		
		if (currentfatigue >= 180 ) {
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 1));
			
			if (randDoublet <= 0.10D) {	
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.tiredtorun")));
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
						("§7Thirst: §f" + currentthirst + " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));
			}
	}
	}
}
