package com.ar.askgaming.betterthings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class Commands implements TabExecutor {
	
	private BetterThings plugin;
	public Commands(BetterThings main) {
		plugin = main;
	}
	
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        
        List<String> list = new ArrayList<>();

        return list;
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
			case "drinks":
				handleDrinksCommand(p, args);
				break;
			default:
				handleBetterThingsCommand(p, args);
				break;
		}


	return false;
	}
	private void handleThirstCommand(Player p, String[] args) {

	}
	private void handleFatigueCommand(Player p, String[] args) {
		
	}
	private void handleBarCommand(Player p, String[] args) {
		
	}
	private void handleDrinksCommand(Player p, String[] args) {
		
	}
	private void handleBetterThingsCommand(Player p, String[] args) {
		switch (args[0].toLowerCase()) {
			case "reload":
				plugin.reloadConfig();
				plugin.loadManagers();
				p.sendMessage("Config reloaded!");
				break;
		
			default:
				break;
		}
	}
}
