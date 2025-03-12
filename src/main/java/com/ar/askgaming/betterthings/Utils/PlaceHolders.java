package com.ar.askgaming.betterthings.Utils;

import org.bukkit.entity.Player;

import com.ar.askgaming.betterthings.BetterThings;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolders extends PlaceholderExpansion {

	private BetterThings plugin;
	public PlaceHolders(BetterThings main) {
		plugin = main;
	}
	
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		
        if(player == null){
            return "";
        }

        if(identifier.equals("thirst")){
			String thirst = String.format("%.1f", plugin.getAttributeManager().getThirst().getAttribute(player));
			return thirst;
        }
        if(identifier.equals("fatigue")){
			String fatigue = String.format("%.1f", plugin.getAttributeManager().getFatigue().getAttribute(player));
			return fatigue;
        }
		if(identifier.equals("fatigue_enabled")){
        	return String.valueOf(plugin.getAttributeManager().getFatigue().hasEnabled(player));
        }
		if(identifier.equals("thirst_enabled")){
        	return String.valueOf(plugin.getAttributeManager().getThirst().hasEnabled(player));
        }
		
		return "Invalid Placeholder";
	}
	
    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
       }
     	
	@Override
	public String getAuthor() {
		return "AMONLY";
	}
	@Override
	public String getIdentifier() {
		return "betterthings";
	}
	@Override
	public String getVersion() {
		return "1.2";
	}
}
