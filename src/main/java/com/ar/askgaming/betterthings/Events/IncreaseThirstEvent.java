package com.ar.askgaming.betterthings.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import com.ar.askgaming.betterthings.BetterThings;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class IncreaseThirstEvent extends Event{

    private BetterThings plugin = BetterThings.getPlugin(BetterThings.class);

    private final HandlerList handlers = new HandlerList();
    private Player player;
    private int thirst;
    private String message;

    public IncreaseThirstEvent(Player player, int thirst,String message) {
        this.player = player;
        this.thirst = thirst;

        if (!plugin.getActionBar().isConstantActionBar()){
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
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
