package com.ar.askgaming.betterthings.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.ar.askgaming.betterthings.Controllers.AttributeManager.AttributeType;

public class AttributeChangeEvent extends Event{

    private AttributeType attribute;
    private Double value;
    private Player player;

    public AttributeChangeEvent(AttributeType attribute, Double value, Player player) {
        this.attribute = attribute;
        this.value = value;
        this.player = player;

    }
    public AttributeType getAttribute() {
        return attribute;
    }

    public Double getValue() {
        return value;
    }

    public Player getPlayer() {
        return player;
    }
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    
}
