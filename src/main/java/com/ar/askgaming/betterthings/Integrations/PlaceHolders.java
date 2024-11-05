package com.ar.askgaming.betterthings.Integrations;

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
        	return String.valueOf(plugin.getThirstManager().getCurrent(player));
        }
        if(identifier.equals("fatigue")){
        	return String.valueOf(plugin.getFatigueManager().getCurrent(player));
        }
		
		return null;
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
		// TODO Auto-generated method stub
		return "betterthings";
	}
	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}
}
