package com.ar.askgaming.betterthings.Managers;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ar.askgaming.betterthings.BetterThings;

public class ThirstManager extends AttributeManager{

    public ThirstManager(BetterThings plugin) {
        super(plugin, 200, "thirst");
    }

    @Override
    protected void handleLowAttribute(Player p) {
        int newThirst = getCurrent(p);

        if (newThirst == 0) {
            if (plugin.getConfig().getBoolean("thirst.hurt_when_0", true)) {
                p.damage(0.5);
            }
        }

        if (newThirst < maxAttribute * 0.25) {
            p.sendMessage(plugin.getFiles().getLang("thirst.very_low"));
            p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 120, 2));
        } else if (newThirst < maxAttribute * 0.5) {
            p.sendMessage(plugin.getFiles().getLang("thirst.low"));
            p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 80, 1));
        }
    }

    @Override
    protected String getConfigKey() {
        return "thirst";
    }

}
