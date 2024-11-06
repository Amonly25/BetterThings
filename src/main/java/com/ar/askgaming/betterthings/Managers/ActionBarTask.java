package com.ar.askgaming.betterthings.Managers;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ActionBarTask extends BukkitRunnable{

    private BetterThings plugin;
    private HashMap<Player, String> emojiMap = new HashMap<>();
    private boolean constantActionBar;

    public boolean isConstantActionBar() {
        return constantActionBar;
    }

    public ActionBarTask(BetterThings main) {
        plugin = main;

        constantActionBar = plugin.getConfig().getBoolean("constant_action_bar");
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            setEmojiMessage(p);
            if (constantActionBar){
                if (plugin.getThirstManager().hasEnabled(p) || plugin.getFatigueManager().hasEnabled(p)){
                    sendMessage(p);
                }
            }
        }
    }

    private void setEmojiMessage(Player p) {
        StringBuilder emojiMessage = new StringBuilder();

        if (plugin.getThirstManager() == null || plugin.getFatigueManager() == null) {
            return;
        }

        int cutoff = (int) Math.round((double) plugin.getThirstManager().getCurrent(p) / 200 * 10);

        for (int i = 0; i < 10; i++) {
            if (i < cutoff) {
                emojiMessage.append(ChatColor.AQUA).append("🍶");
            } else {
                emojiMessage.append(ChatColor.GRAY).append("🍶");
            }
        }

        emojiMessage.append("       "); // Add some space between the two sets of emojis

        int cutoff2 = (int) Math.round((double) plugin.getFatigueManager().getCurrent(p) / 200 * 10);

        for (int i = 0; i < 10; i++) {
            if (i < (10 - cutoff2)) {
                emojiMessage.append(ChatColor.GRAY).append("🛌");
            } else {
                emojiMessage.append(ChatColor.RED).append("🛌");
            }
        }

        emojiMap.put(p, emojiMessage.toString());
    }
    public String getEmojiMessage(Player p) {
        return emojiMap.getOrDefault(p, "");
    }
    public void sendMessage(Player p) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(getEmojiMessage(p)));
    }
}
