package de.spookly;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.jetbrains.annotations.NotNull;

import de.spookly.phase.GamePhaseManager;
import de.spookly.placeholder.PlaceholderManager;
import de.spookly.player.SpooklyOfflinePlayer;
import de.spookly.player.SpooklyPlayer;
import de.spookly.punishments.Punishment;
import de.spookly.team.Team;
import de.spookly.team.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Spookly {
    private static SpooklyServer server;

    private Spookly() {
    }

    @NotNull
    public static SpooklyServer getServer() {
        return server;
    }

    public static void setSpooklyServer(@NotNull SpooklyServer spooklyServer) {
        if (Spookly.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Server");
        }

        server = spooklyServer;
        Bukkit.getLogger().info("Spookly-Core initialized.");
    }

    public static void getOfflinePlayer(@Nonnull String name, Consumer<SpooklyOfflinePlayer> consumer) {
        server.getOfflinePlayer(name, consumer);
    }

    public static void getOfflinePlayer(@Nonnull UUID uuid, Consumer<SpooklyOfflinePlayer> consumer) {
        server.getOfflinePlayer(uuid, consumer);
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

    public static PlaceholderManager getPlaceholderManager() {
        return server.getPlaceholderManager();
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

    public static Punishment.Builder buildPunishment(SpooklyOfflinePlayer target, SpooklyPlayer source) {
        return server.getPunishmentBuilder(target, source);
    }
}
