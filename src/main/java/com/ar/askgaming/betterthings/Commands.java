package com.ar.askgaming.betterthings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;
import com.ar.askgaming.betterthings.Drinks.Items;

public class Commands implements TabExecutor {
	
	private BetterThings plugin;
	private Thirst thirst;
	private Fatigue fatigue;
	public Commands(BetterThings main) {
		plugin = main;
		thirst = plugin.getAttributeManager().getThirst();
		fatigue = plugin.getAttributeManager().getFatigue();

		plugin.getServer().getPluginCommand("thirst").setExecutor(this);
		plugin.getServer().getPluginCommand("fatigue").setExecutor(this);
		plugin.getServer().getPluginCommand("bar").setExecutor(this);
		plugin.getServer().getPluginCommand("betterthings").setExecutor(this);

	}
	
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        
		switch (cmd.getName().toLowerCase()) {

			case "thirst":
			case "fatigue":
				return List.of("toggle");

			case "betterthings":
				if (args.length == 1) {
					return List.of("set", "reload","get_item");
				}
				
				if (args[0].equalsIgnoreCase("get_item")){
					List<String> list = new ArrayList<>();
					for (Items.DrinkType drink : Items.DrinkType.values()) {
                        list.add(drink.toString().toLowerCase());
                    }
					list.add("lemon");
					return list;
				}

			default:
				return null;
		}
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args ) {
		
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can use this command!");
			return true;
		}
		Player p = (Player) sender;

		switch (cmd.getName().toLowerCase()) {
			case "thirst":
				handleThirstCommand(p, args);
				break;
			case "fatigue":
				handleFatigueCommand(p, args);
				break;
			case "bar":
				handleBarCommand(p, args);
				break;
			default:
				handleBetterThingsCommand(p, args);
				break;
		}
		return false;
	}
	
	private void handleThirstCommand(Player p, String[] args) {

		if (args.length == 0) {
			String value = String.format("%.1f", thirst.getAttribute(p));
			p.sendMessage(plugin.getFiles().getLang("thirst.on_cmd").replace("%amount%", value).replace("%percent%", thirst.getPercent(p)));
			
		} else if (args[0].equalsIgnoreCase("toggle")) {
			thirst.toggle(p);
		}

	}
	private void handleFatigueCommand(Player p, String[] args) {

		if (args.length == 0) {
			String value = String.format("%.1f", fatigue.getAttribute(p));

			p.sendMessage(plugin.getFiles().getLang("fatigue.on_cmd").replace("%amount%", value).replace("%percent%", fatigue.getPercent(p)));
			
		} else if (args[0].equalsIgnoreCase("toggle")) {
			fatigue.toggle(p);
		}
		
	}
	private void handleBarCommand(Player p, String[] args) {
		p.openInventory(plugin.getBarShop().getInv());
	}

	private void handleBetterThingsCommand(Player p, String[] args) {

		if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			plugin.getActionBar().reload();
			thirst.loadData();
			fatigue.loadData();
			plugin.getFiles().savePlayerData();
			plugin.getFiles().reloadFiles();
			return;
		}

		if (args.length == 2 && args[0].equalsIgnoreCase("get_item")) {
			String itemType = args[1].toLowerCase();
			if (plugin.getItems().getFromString(itemType) != null) {
				p.getInventory().addItem(plugin.getItems().getFromString(itemType));
				return;
			} else if (itemType.equals("lemon")){
				p.getInventory().addItem(plugin.getItems().getlemon());
			}
		}

		if (args[0].equalsIgnoreCase("set")) {
			if (args.length != 4) {
				p.sendMessage("Invalid usage, use /betterthings set <player> <thirst/fatigue> <amount>");
				return;
			}
			Player target = plugin.getServer().getPlayer(args[1]);
			if (target == null) {
				p.sendMessage("Player not found");
				return;
			}
			double amount = 0;
			try {
				amount = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				p.sendMessage("Invalid number");
				return;
			}
			if (args[2].equalsIgnoreCase("thirst")) {
				thirst.setAttribute(target, amount);
				p.sendMessage("Thirst set to " + amount + " for " + target.getName());
				return;
			}
			if (args[2].equalsIgnoreCase("fatigue")) {
				fatigue.setAttribute(target, amount);
				p.sendMessage("Fatigue set to " + amount + " for " + target.getName());
				return;
			}
			return;
		}
		p.sendMessage("Invalid argument");
		
	}
}
