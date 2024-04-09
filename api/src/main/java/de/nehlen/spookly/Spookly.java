package de.nehlen.spookly;

import de.nehlen.spookly.phase.GamePhase;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public final class Spookly {
    @Getter private static SpooklyServer server;

    private Spookly() {}

    public static void setSpooklyServer(@NotNull SpooklyServer server) {
        if(Spookly.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Server");
        }

        Spookly.server = server;
        Bukkit.getLogger().info("Spookly-Core initialized.");
    }

    public static SpooklyOfflinePlayer getOfflinePlayer(@Nonnull String name) {
        return server.getOfflinePlayer(name);
    }

    public static SpooklyOfflinePlayer getOfflinePlayer(@Nonnull UUID uuid) {
        return server.getOfflinePlayer(uuid);
    }

    public static SpooklyPlayer getPlayer(@Nonnull Player player) {
        return server.getPlayer(player);
    }

    public static SpooklyPlayer getPlayer(@Nonnull UUID uuid) {
        return server.getPlayer(uuid);
    }

    public static List<SpooklyPlayer> getOnlinePlayers() {
        return server.getOnlinePlayers();
    }

    public static TeamManager getTeamManager() {
        return server.getTeamManager();
    }

    public static GamePhaseManager getGamePhaseManager() {
        return server.getGamePhaseManager();
    }

    public static Team.Builder buildTeam() {
        return server.buildTeam();
    }
}
