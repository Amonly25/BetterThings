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
            double amount = plugin.getConfig().getDouble("thirst.damage_when_0", 1);
            p.damage(amount);
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
    public String getConfigKey() {
        return "thirst";
    }

}
