package betterthings;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Commands implements CommandExecutor {
	
	static Main plugin;
	public Commands(Main main) {
		plugin = main;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args ) {
		
		Player p = (Player) sender; 
		
		List<String> help = plugin.getConfig().getStringList("messages.help");
				
    	if (label.equalsIgnoreCase("thirst")) {
    		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�7Thirst: �f" + String.valueOf(plugin.getConfig().getInt("players.thirst." + p.getUniqueId())) + " �7||||"));
    		p.sendMessage("�7Thirst: �f" + String.valueOf(plugin.getConfig().getInt("players.thirst." + p.getUniqueId())) + " �b||||");
    		return true;
    	}
    	if (label.equalsIgnoreCase("fatigue")) {
    		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�7Fatigue: �f" + String.valueOf(plugin.getConfig().getInt("players.fatigue." + p.getUniqueId())) + " �7||||"));
    		p.sendMessage("�7Fatigue: �f" + String.valueOf(plugin.getConfig().getInt("players.fatigue." + p.getUniqueId())) + " �b||||");
    		return true;
    	}
    	if (label.equalsIgnoreCase("betterthings")) {
 
    		if (args.length == 1) {
    	       			
		    	if (sender.hasPermission("betterthings.items")) {
		    		
		    		switch(args[0]) {
		    		
		    			case "coffe": {
							p.getInventory().addItem(plugin.cafe);
							return true;   							
				    	}
		    			case "milk": {
							p.getInventory().addItem(plugin.leche);
							return true;   			
				    	}
		    			case "wine": {
							p.getInventory().addItem(plugin.vino);
							return true;   			
				    	}
		    			case "lemonade": {
							p.getInventory().addItem(plugin.limonada);
							return true;   			
				    	}
		    			case "juice": {
							p.getInventory().addItem(plugin.jugo);
							return true;   			
				    	}
		    			case "godjuice": {
							p.getInventory().addItem(plugin.jugodioses);
							return true;   			
				    	}
		    		}
				if (sender.hasPermission("betterthings.admin")) {
					
					switch(args[0]) {
					
						case "maxfatigue": { 
				    		plugin.getConfig().set("players.fatigue." + p.getUniqueId(), 180);
				    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onfatiguechange")));	
				    		return true;
				    	}
						case "maxthirst": { 
				    		plugin.getConfig().set("players.thirst." + p.getUniqueId(), 180);
				    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onthirstchange")));	
				    		return true;
				    	}
						case "refreshthirst": { 	
				    		plugin.getConfig().set("players.thirst." + p.getUniqueId(), 0);
				    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onthirstchange")));
				    		return true;
				    	}
						case "refreshfatigue": { 
				    		plugin.getConfig().set("players.fatigue." + p.getUniqueId(), 0);	
				    		p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onfatiguechange")));
				    		return true;
				    		}
						}
					}
			    }
    		}
    		if (args.length == 2) {
    			
				if (sender.hasPermission("betterthings.admin")) {
					
					switch(args[0]) {
					
						case "maxfatigue": { 
							
								if (Bukkit.getPlayerExact(args[1]) != null) {
									Player target = Bukkit.getPlayer(args[1]);
									
										plugin.getConfig().set("players.fatigue." + target.getUniqueId(), 180);
										target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�7Fatigue: �f" + String.valueOf(plugin.getConfig().getInt("players.fatigue." + p.getUniqueId())) + " �7||||"));
										target.sendMessage("�7Fatigue: �f" + String.valueOf(plugin.getConfig().getInt("Players.fatigue." + p.getUniqueId())) + " �b||||");
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.ongivefatigue")) + target.getName());
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onfatiguechange")));
										return true;
									}
								}
				    											    	
						case "maxthirst": { 
							
								if (Bukkit.getPlayerExact(args[1]) != null) {
									Player target = Bukkit.getPlayer(args[1]);
									
									plugin.getConfig().set("players.thirst." + target.getUniqueId(), 180);
									target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�7Thirst: �f" + String.valueOf(plugin.getConfig().getInt("players.thirst." + p.getUniqueId())) + " �7||||"));
									target.sendMessage("�7Thirst: �f" + String.valueOf(plugin.getConfig().getInt("players.thirst." + p.getUniqueId())) + " �b||||");
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.ongivethirst")) + target.getName());
									target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onthirstchange")));
									return true;
								}										    			
							}		    	
						case "refreshthirst": { 	
							
								if (Bukkit.getPlayerExact(args[1]) != null) {
									Player target = Bukkit.getPlayer(args[1]);
									
										plugin.getConfig().set("players.fatigue." + target.getUniqueId(), 180);
										target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�7Fatigue: �f" + String.valueOf(plugin.getConfig().getInt("players.fatigue." + p.getUniqueId())) + " �7||||"));
										target.sendMessage("�7Fatigue: �f" + String.valueOf(plugin.getConfig().getInt("Players.fatiga." + p.getUniqueId())) + " �b||||");
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onrefreshthirst")) + target.getName());
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onthirstchange")));
										return true;
									}								
				    			}								    	
						case "refreshfatigue": {
							
								if (Bukkit.getPlayerExact(args[1]) != null) {
									Player target = Bukkit.getPlayer(args[1]);
									
										plugin.getConfig().set("players.thirst." + target.getUniqueId(), 180);
										target.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("�7Thirst: �f" + String.valueOf(plugin.getConfig().getInt("players.thirst." + p.getUniqueId())) + " �7||||"));
										target.sendMessage("�7Thirst: �f" + String.valueOf(plugin.getConfig().getInt("players.thirst." + p.getUniqueId())) + " �b||||");
										sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onrefreshfatigue")) + target.getName());
										target.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.onfatiguechange")));
										return true;
							}						
						}
					}
				}
    		}   		
		}
    	for (String m : help) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
			break;
		} 	
		return false;
	}
}
