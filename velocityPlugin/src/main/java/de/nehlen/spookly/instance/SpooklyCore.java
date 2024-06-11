package de.nehlen.spookly.instance;

import de.nehlen.spookly.SpooklyServer;
import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.database.Connection;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class SpooklyCore implements SpooklyServer {
    @Override
    public SpooklyOfflinePlayer getOfflinePlayer(@NotNull String name) {
        return null;
    }

    @Override
    public SpooklyOfflinePlayer getOfflinePlayer(@NotNull UUID uuid) {
        return null;
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull UUID uuid) {
        return null;
    }

    @Override
    public List<SpooklyPlayer> getOnlinePlayers() {
        return List.of();
    }

    @Override
    public EventExecuter getEventExecuter() {
        return null;
    }

    @Override
    public String getSchemaPrefix() {
        return "";
    }

    @Override
    public GamePhaseManager getGamePhaseManager() {
        return null;
    }

    @Override
    public TeamManager getTeamManager() {
        return null;
    }

    @Override
    public Team.Builder buildTeam() {
        return null;
    }

    @Override
    public ConfigurationWrapper createConfiguration(File file) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull org.bukkit.entity.Player player) {
        return null;
    }
}
