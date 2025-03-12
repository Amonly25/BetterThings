package com.ar.askgaming.betterthings.Controllers;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;
import com.ar.askgaming.betterthings.Attribute.Fatigue;
import com.ar.askgaming.betterthings.Attribute.Thirst;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionBarTask extends BukkitRunnable{

    private final BetterThings plugin;
    private HashMap<Player, String> emojiMap = new HashMap<>();
    private boolean constantActionBar;
    private boolean disabled;

    public ActionBarTask(BetterThings main) {
        plugin = main;

        runTaskTimer(plugin, 60, 40);
        reload();
    }
    public void reload(){
        constantActionBar = plugin.getConfig().getBoolean("constant_action_bar", true);
        disabled = plugin.getConfig().getBoolean("disable_action_bar", false);
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            setEmojiMessage(p);
            if (constantActionBar){
                if (plugin.getAttributeManager().getFatigue().hasEnabled(p) || plugin.getAttributeManager().getThirst().hasEnabled(p)){
                    sendMessage(p);
                }
            }
        }
    }

    private void setEmojiMessage(Player p) {
        StringBuilder emojiMessage = new StringBuilder();

        Thirst thirst = plugin.getAttributeManager().getThirst();
        double playerThirst = thirst.getAttribute(p);
        double maxThirst = thirst.getMaxAttribute();
        String emoji = thirst.getEmoji();
        int cutoff = (int) Math.round((double) playerThirst / maxThirst* 10);

        for (int i = 0; i < 10; i++) {
            if (i < cutoff) {
                emojiMessage.append(ChatColor.AQUA).append(emoji);
            } else {
                emojiMessage.append(ChatColor.GRAY).append(emoji);
            }
        }

        emojiMessage.append("       "); // Add some space between the two sets of emojis

        Fatigue fatigue = plugin.getAttributeManager().getFatigue();
        double playerFatigue = fatigue.getAttribute(p);
        double maxFatigue = fatigue.getMaxAttribute();
        String emoji2 = fatigue.getEmoji();

        int cutoff2 = (int) Math.round((double) playerFatigue / maxFatigue * 10);

        for (int i = 0; i < 10; i++) {
            if (i < (10 - cutoff2)) {
                emojiMessage.append(ChatColor.GRAY).append(emoji2);
            } else {
                emojiMessage.append(ChatColor.RED).append(emoji2);
            }
        }

        emojiMap.put(p, emojiMessage.toString());
    }
    public String getEmojiMessage(Player p) {
        return emojiMap.getOrDefault(p, "");
    }
    public void sendMessage(Player p) {
        if (disabled) {
            return;
        }
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(getEmojiMessage(p)));
    }
}
