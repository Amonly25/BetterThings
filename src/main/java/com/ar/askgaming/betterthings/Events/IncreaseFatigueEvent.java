package com.ar.askgaming.betterthings.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class IncreaseFatigueEvent extends Event{

    public IncreaseFatigueEvent(Player player, int fatigue) {
        this.player = player;
        this.fatigue = fatigue;

    }

    private final HandlerList handlers = new HandlerList();
    private Player player;
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
