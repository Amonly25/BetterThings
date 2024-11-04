package betterthings;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import betterthings.events.BlockEvent;
import betterthings.events.ConsumeEvent;
import betterthings.events.DeathEvent;
import betterthings.events.MoveEvent;
import betterthings.events.RegenEvent;
import betterthings.events.SleepEvent;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		
		this.saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
		getServer().getPluginManager().registerEvents(new MoveEvent(this), this);
		getServer().getPluginManager().registerEvents(new ConsumeEvent(this), this);
		getServer().getPluginManager().registerEvents(new RegenEvent(this), this);
		getServer().getPluginManager().registerEvents(new BlockEvent(this), this);
		getServer().getPluginManager().registerEvents(new SleepEvent(this), this);
		
		Bukkit.getPluginCommand("thirst").setExecutor(new Commands(this));
		Bukkit.getPluginCommand("fatigue").setExecutor(new Commands(this));
		Bukkit.getPluginCommand("betterthings").setExecutor(new Commands(this));
		
		int thirsttask = this.getConfig().getInt("thirst.task");
		int fatiguetask = this.getConfig().getInt("fatigue.task");
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ThirstTask(this), 0, thirsttask);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new FatigueTask(this), 0, fatiguetask);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.getLogger().info("PlaceholderAPI found!");
            new PlaceHolders(this).register();
        } else {
            this.getLogger().info("PlaceholderAPI not found!");
        } 
        
        System.out.println("\n§3Enabling BetherThings 1.0\n");
        
        NamespacedKey juicer = new NamespacedKey(this, "juice");
        ShapedRecipe recipelimonada = new ShapedRecipe(juicer, jugo);
        recipelimonada.shape("EEE", " C ", "   ");
        recipelimonada.setIngredient('E', Material.APPLE);
        recipelimonada.setIngredient('C', Material.GLASS_BOTTLE);
        Bukkit.addRecipe(recipelimonada);
        
        NamespacedKey lemonader = new NamespacedKey(this, "god_juice");
        ShapedRecipe recipelimonadad = new ShapedRecipe(lemonader, jugodioses);
        recipelimonadad.shape("AAA", " B ", "   ");
        recipelimonadad.setIngredient('A', Material.GOLDEN_APPLE);
        recipelimonadad.setIngredient('B', Material.GLASS_BOTTLE);
        Bukkit.addRecipe(recipelimonadad); 
                
	}
	
	private String color(String s){
	    return ChatColor.translateAlternateColorCodes('&', s);
		}
	private List<String> color(List<String> lore){
	    return lore.stream().map(this::color).collect(Collectors.toList());
	}
	
	List<String> cafelore = this.getConfig().getStringList("items.coffelore");
	List<String> lechelore = this.getConfig().getStringList("items.milklore");
	List<String> vinolore = this.getConfig().getStringList("items.winelore");
	List<String> limonadalore = this.getConfig().getStringList("items.lemonadelore");
	List<String> jugodioseslore = this.getConfig().getStringList("items.godjuicelore");
	List<String> jugolore = this.getConfig().getStringList("items.juicelore");
	
	public ItemStack cafe = new ItemStack(Material.POTION, 1); {
	PotionMeta cafemeta = (PotionMeta) cafe.getItemMeta();
	cafemeta.setColor(Color.BLACK);
	cafemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("items.coffename")));
	cafemeta.setLore(this.color(cafelore));
	cafemeta.setBasePotionData(new PotionData(PotionType.WATER));
	cafemeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	cafe.setItemMeta(cafemeta);
	}
	
	public ItemStack leche = new ItemStack(Material.POTION, 1); {
	PotionMeta lechemeta = (PotionMeta) leche.getItemMeta();
	lechemeta.setColor(Color.WHITE);
	lechemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("items.milkname")));
	lechemeta.setLore(this.color(lechelore));
	lechemeta.setBasePotionData(new PotionData(PotionType.WATER));
	lechemeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	leche.setItemMeta(lechemeta);
	}
	
	public ItemStack vino = new ItemStack(Material.POTION, 1); {
	PotionMeta vinometa = (PotionMeta) vino.getItemMeta();
	vinometa.setColor(Color.MAROON);
	vinometa.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("items.winename")));
	vinometa.setLore(this.color(vinolore));
	vinometa.setBasePotionData(new PotionData(PotionType.WATER));
	vinometa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	vino.setItemMeta(vinometa);
	}
	
	public ItemStack limonada = new ItemStack(Material.POTION, 1); {
	PotionMeta limonadameta = (PotionMeta) limonada.getItemMeta();
	limonadameta.setColor(Color.LIME);
	limonadameta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("items.lemonadename")));
	limonadameta.setLore(this.color(limonadalore));
	limonadameta.setBasePotionData(new PotionData(PotionType.WATER));
	limonadameta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	limonada.setItemMeta(limonadameta);
	}
	
	public ItemStack limon = new ItemStack(Material.LIME_DYE); {
	ItemMeta limonmeta = (ItemMeta) limon.getItemMeta();
	limonmeta.setDisplayName("§aLimon");
	limonmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	limon.setItemMeta(limonmeta);
	}
	
	public ItemStack jugo = new ItemStack(Material.POTION, 1); {
	PotionMeta jugometa = (PotionMeta) jugo.getItemMeta();
	jugometa.setColor(Color.RED);
	jugometa.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("items.juicename")));
	jugometa.setLore(this.color(jugolore));
	jugometa.setBasePotionData(new PotionData(PotionType.WATER));
	jugometa.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	jugo.setItemMeta(jugometa);
	}
	
	public ItemStack jugodioses = new ItemStack(Material.POTION, 1); {
	PotionMeta jugodiosesmeta = (PotionMeta) jugodioses.getItemMeta();
	jugodiosesmeta.setColor(Color.YELLOW);
	jugodiosesmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("items.godjuicename")));
	PotionEffect vel = new PotionEffect(PotionEffectType.ABSORPTION, 3000, 1);
	jugodiosesmeta.addCustomEffect(vel, true);
	jugodiosesmeta.setBasePotionData(new PotionData(PotionType.WATER));
	jugodiosesmeta.setLore(this.color(jugodioseslore));
	jugodiosesmeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
	jugodioses.setItemMeta(jugodiosesmeta);
	}
	
}


