package betterthings.events;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import betterthings.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ConsumeEvent implements Listener{
		
	static Main plugin;
	public ConsumeEvent(Main main) {
		plugin = main;
	}	
		
	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent e) {
		
		Player p = (Player) e.getPlayer();
		ItemStack item = e.getItem();
		int currentthirst = plugin.getConfig().getInt("players.thirst." + p.getUniqueId());
		int currentfatigue = plugin.getConfig().getInt("players.fatigue." + p.getUniqueId());		
		
		if (currentthirst >= 50) {
        	if (item != null && item.getType() != null ) {
        		if (item.getType().equals(Material.MILK_BUCKET)) {
        			
					plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 50));						
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
							("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." + p.getUniqueId()) 
									+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));
					
        		}
        		if (item.hasItemMeta() && item.getType().equals(Material.POTION)) {
        			
        			PotionMeta pm = (PotionMeta) item.getItemMeta();
        			
        			if (pm.getBasePotionData().getType() == PotionType.WATER) {
        				
        				if(!pm.hasColor()){
        					
        					plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 50));
        					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
        							("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." + p.getUniqueId()) 
        									+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));
        				}
        				else if(pm.getColor().equals(Color.WHITE)){
        					
		    				plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 60));
		    				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
		    						("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." +  p.getUniqueId()) 
		    								+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||")); 			
		        		}
	        			else if(pm.getColor().equals(Color.MAROON)){
	        				
		    				plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 40));
		    				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
		    						("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." +  p.getUniqueId()) 
		    								+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||")); 			
		        		}
	        			else if(pm.getColor().equals(Color.BLACK)){
	        				
		    				plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 25));
		    				plugin.getConfig().set("players.fatigue." + p.getUniqueId(), (currentfatigue - 25));
		    				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
		    						("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." + p.getUniqueId()) 
		    								+ " §b|||| §7- Fatigue: §f" + plugin.getConfig().getInt("players.fatigue." 
		    								+ p.getUniqueId()) + " §b||||"));			
		        		}
	        			else if(pm.getColor().equals(Color.LIME)){
	        				
		    				plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 70));
		    				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
		    						("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." +  p.getUniqueId()) 
		    								+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||")); 			
		        		}
	        			else if(pm.getColor().equals(Color.RED)){
	        				
		    				plugin.getConfig().set("players.thirst." + p.getUniqueId(), (currentthirst - 60));
		    				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
		    						("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." +  p.getUniqueId()) 
		    								+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||"));			
		        		}
	        			else if(pm.getColor().equals(Color.YELLOW)){
	        				
		    				plugin.getConfig().set("players.thirst." + p.getUniqueId(), 0);
		    				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText
		    						("§7Thirst: §f" + plugin.getConfig().getInt("players.thirst." +  p.getUniqueId()) 
		    								+ " §b|||| §7- Fatigue: §f" + currentfatigue + " §b||||")); 			
		        		}
	        		}
	        	}
			}
		}
	}	
}
