package co.insou.pvpglow;

import co.insou.pvpglow.config.Config;
import co.insou.pvpglow.listeners.PvPListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;
    private PvPListener pvp;

    @Override
    public void onEnable() {
        config = new Config(this);
        pvp = new PvPListener(this);
        getServer().getPluginManager().registerEvents(pvp, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvpglow")) {
            if (args.length < 1) {
                playHelp(sender);
                return false;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("pvpglow.reload")) {
                    sender.sendMessage(ChatColor.RED + "You require permission pvpglow.reload to do this!");
                    return false;
                }
                reloadConfig();
                config = new Config(this);
                sender.sendMessage(ChatColor.GREEN + "PvPGlow configuration reloaded!");
                return false;
            }
            playHelp(sender);
        }
        return false;
    }

    private void playHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + "==< PvPGlow Help >==");
        sender.sendMessage(ChatColor.DARK_GRAY + "> /pvpglow reload - Reloads the configuration");
    }

    public Config getPvpConfig() {
        return config;
    }

}
