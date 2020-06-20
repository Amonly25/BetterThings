package betterthings;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolders extends PlaceholderExpansion {

	static Main plugin;
	public PlaceHolders(Main main) {
		plugin = main;
	}
	
	@Override
	public String onPlaceholderRequest(Player player, String identifier) {
		
        if(player == null){
            return "";
        }

        if(identifier.equals("thirst")){
        	return String.valueOf(plugin.getConfig().getInt("players.thirst." + player.getUniqueId()));
        }
        if(identifier.equals("fatigue")){
        	return String.valueOf(plugin.getConfig().getInt("players.fatigue." + player.getUniqueId()));
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
