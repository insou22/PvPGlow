package co.insou.pvpglow.listeners;

import co.insou.pvpglow.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class PvPListener implements Listener {

    private final Main plugin;
    private final Map<Player, Integer> ids = new HashMap<>();

    public PvPListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            final Player damaged = (Player) event.getEntity();
            if (!damaged.hasPermission("pvpglow.bypass")) {
                Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("pvpglow");
                if (team == null) {
                    team = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("pvpglow");
                    team.setPrefix(plugin.getPvpConfig().getColour().toString());
                    team.setSuffix(ChatColor.RESET.toString());
                } else if (!team.getPrefix().equalsIgnoreCase(plugin.getPvpConfig().getColour().toString())) {
                    team.setPrefix(plugin.getPvpConfig().getColour().toString());
                }
                team.addEntry(damaged.getName());
                damaged.setGlowing(true);
                final Team finalTeam = team;
                final int[] id = {-1};
                id[0] = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (ids.get(damaged) == id[0]) {
                            damaged.setGlowing(false);
                            finalTeam.removeEntry(damaged.getName());
                        }
                    }
                }, plugin.getPvpConfig().getTickLength());
                ids.put(damaged, id[0]);
            }
        }
    }

}
