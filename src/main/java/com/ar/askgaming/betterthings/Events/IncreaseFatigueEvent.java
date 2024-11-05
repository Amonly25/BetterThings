package com.ar.askgaming.betterthings.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class IncreaseFatigueEvent extends Event{

    private BetterThings plugin = BetterThings.getPlugin(BetterThings.class);

    public IncreaseFatigueEvent(Player player, int fatigue, String message) {
        this.player = player;
        this.fatigue = fatigue;

        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= 5) {
                    cancel();
                    return;
                }
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(message));
                count++;
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    private final HandlerList handlers = new HandlerList();
    private Player player;
    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private int fatigue;

    public Player getPlayer() {
        return player;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
