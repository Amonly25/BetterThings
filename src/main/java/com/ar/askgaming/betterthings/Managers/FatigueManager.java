package com.ar.askgaming.betterthings.Managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;

public class FatigueManager extends AttributeManager {

    public FatigueManager(BetterThings plugin) {
        super(plugin, 250, "fatigue");
    }

    @Override
    protected void handleLowAttribute(Player p) {
        int newFatigue = getCurrent(p);
        Bukkit.broadcastMessage("test" + getConfigKey());
        if (newFatigue == 0) {
            if (plugin.getConfig().getBoolean("fatigue.hurt_when_0", true)) {
                p.damage(0.5);
            }
        }

        if (newFatigue < maxAttribute * 0.25) {
            p.sendMessage(plugin.getFiles().getLang("fatigue.very_low"));
            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 80, 1));
        } else if (newFatigue < maxAttribute * 0.5) {
            p.sendMessage(plugin.getFiles().getLang("fatigue.low"));
            p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 40, 1));
        }
    }

    @Override
    protected String getConfigKey() {
        return "fatigue";
    }
}
