package com.ar.askgaming.betterthings.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ar.askgaming.betterthings.BetterThings;

public class IncreaseThirstEvent extends Event{

    private final HandlerList handlers = new HandlerList();
    private BetterThings plugin = BetterThings.getPlugin(BetterThings.class);
    private Player player;
    private int thirst;

    public IncreaseThirstEvent(Player player, int thirst) {
        this.player = player;
        this.thirst = thirst;

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

}
