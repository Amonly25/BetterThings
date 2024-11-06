package com.ar.askgaming.betterthings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.ar.askgaming.betterthings.Managers.FatigueManager;
import com.ar.askgaming.betterthings.Managers.ThirstManager;

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
			default:
				handleBetterThingsCommand(p, args);
				break;
		}


	return false;
	}
	private void handleThirstCommand(Player p, String[] args) {

		ThirstManager t = plugin.getThirstManager();

		if (args.length == 0) {
			p.setSleepingIgnored(true);
			if (t.hasEnabled(p)){
				p.sendMessage(plugin.getFiles().getLang("thirst.on_cmd")
				.replace("%amount%", String.valueOf(t.getCurrent(p)))
				.replace("%percent%", t.getPercent(p)));
				return;
			}
			p.sendMessage(plugin.getFiles().getLang("thirst.disabled"));
			
		} else if (args[0].equalsIgnoreCase("toggle")) {
			t.toggle(p);
		}

	}
	private void handleFatigueCommand(Player p, String[] args) {

		FatigueManager f = plugin.getFatigueManager();

		if (args.length == 0) {

			if (f.hasEnabled(p)){
				p.sendMessage(plugin.getFiles().getLang("fatigue.on_cmd")
				.replace("%amount%", String.valueOf(f.getCurrent(p)))
				.replace("%percent%", f.getPercent(p)));
				return;
			}
			p.sendMessage(plugin.getFiles().getLang("fatigue.disabled"));

		} else if (args[0].equalsIgnoreCase("toggle")) {
			f.toggle(p);
		}
		
	}
	private void handleBarCommand(Player p, String[] args) {
		p.openInventory(plugin.getBarShop().getInv());
	}

	private void handleBetterThingsCommand(Player p, String[] args) {

		if (args[0].equalsIgnoreCase("reload")) {
			plugin.reloadConfig();
			plugin.loadManagers();
			return;
		}

		if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
			Player target = plugin.getServer().getPlayer(args[2]);
			if (target == null) {
				p.sendMessage("Player not found");
				return;
			}
			switch (args[1]) {
				case "max_thirst":
					plugin.getThirstManager().setAttribute(target, 200);
					break;
				case "max_fatigue":
					plugin.getFatigueManager().setAttribute(target, 2050);
					break;		
				case "min_thirst":
					plugin.getThirstManager().setAttribute(target, 0);
					break;
				case "min_fatigue":
					plugin.getFatigueManager().setAttribute(target, 0);
					break;		
				default:
					p.sendMessage("Invalid argument");
					break;
			}
		}
		p.sendMessage("Invalid argument");
		
	}
}
