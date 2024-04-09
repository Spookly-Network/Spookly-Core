package de.nehlen.spookly.instance;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.SpooklyServer;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamImpl;
import de.nehlen.spookly.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class SpooklyCore implements SpooklyServer {

    public SpooklyCore() {
        Spookly.setSpooklyServer(this);
    }

    @Override
    public SpooklyOfflinePlayer getOfflinePlayer(@NotNull String name) {
        return SpooklyCorePlugin.getInstance().getPlayerSchema().getOfflinePlayer(name);
    }

    @Override
    public SpooklyOfflinePlayer getOfflinePlayer(@NotNull UUID uuid) {
        return SpooklyCorePlugin.getInstance().getPlayerSchema().getOfflinePlayer(uuid);
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull Player player) {
        return SpooklyCorePlugin.getInstance().getPlayerSchema().getPlayer(player);
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull UUID uuid) {
        return SpooklyCorePlugin.getInstance().getPlayerSchema().getPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public List<SpooklyPlayer> getOnlinePlayers() {
        return SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().values().stream().toList();
    }

    @Override
    public EventExecuter getEventExecuter() {
        return SpooklyCorePlugin.getInstance().getEventExecuter();
    }

    @Override
    public String getSchemaPrefix() {
        return SpooklyCorePlugin.getInstance().getDatabaseConfiguration().getOrSetDefault("database.schemaPrefix", "spookly_");
    }

    @Override
    public GamePhaseManager getGamePhaseManager() {
        return SpooklyCorePlugin.getInstance().getGamePhaseManager();
    }

    @Override
    public TeamManager getTeamManager() {
        return SpooklyCorePlugin.getInstance().getTeamManager();
    }

    @Override
    public Team.Builder buildTeam() {
        return TeamImpl.builder();
    }
}
