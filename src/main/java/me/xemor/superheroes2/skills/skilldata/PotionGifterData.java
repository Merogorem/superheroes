package me.xemor.superheroes2.skills.skilldata;

import me.xemor.superheroes2.skills.Skill;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class PotionGifterData extends PotionEffectData {

    private int cooldown;
    private String receiverMessage;
    private String giverMessage;
    private String cooldownMessage;

    protected PotionGifterData(Skill skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        cooldown = configurationSection.getInt("cooldown", 10);
        receiverMessage = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("receiverMessage", "&7&oYou have received a potion effect."));
        giverMessage = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("giverMessage", "&7&oYou gave them a potion effect."));
        cooldownMessage = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("cooldownMessage", "&7&oYou have %s seconds left until it can be used again!"));
    }

    public String getGiverMessage() {
        return giverMessage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public String getCooldownMessage() {
        return cooldownMessage;
    }
}
