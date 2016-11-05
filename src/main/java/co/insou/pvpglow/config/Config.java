package co.insou.pvpglow.config;

import co.insou.pvpglow.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private final Main plugin;
    private FileConfiguration config;

    private long tickLength = 100;
    private ChatColor colour = ChatColor.GREEN;

    public Config(Main plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        long tickLength = config.getLong("duration-seconds") * 20;
        if (tickLength > 0) {
            this.tickLength = tickLength;
        }
        try {
            ChatColor colour = ChatColor.getByChar(config.getString("color-code"));
            if (colour != null) {
                this.colour = colour;
            }
        } catch (Exception ignored) {}
    }

    public long getTickLength() {
        return tickLength;
    }

    public ChatColor getColour() {
        return colour;
    }


}
