package de.nehlen.spookly.instance;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.SpooklyServer;
import de.nehlen.spookly.configuration.Config;
import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.database.Connection;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.placeholder.PlaceholderManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishment.PunishmentImpl;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamImpl;
import de.nehlen.spookly.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
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
        if(SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().containsKey(player.getUniqueId())) {
            return SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().get(player.getUniqueId());
        }

        return SpooklyCorePlugin.getInstance().getPlayerSchema().getPlayer(player);
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull UUID uuid) {
        if(SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().containsKey(uuid)) {
            return SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().get(uuid);
        }

        return SpooklyCorePlugin.getInstance().getPlayerSchema().getPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public List<SpooklyPlayer> getOnlinePlayers() {
        //Check beforehand if empty because of wired IndexOutOfBoundsException? Also shouldn't be empty when called
        if (SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().values());
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

    @Override
    public ConfigurationWrapper createConfiguration(File file) {
        return new Config(file);
    }

    @Override
    public Connection getConnection() {
        return SpooklyCorePlugin.getInstance().getConnection();
    }

    @Override
    public Punishment.Builder getPunishmentBuilder(SpooklyOfflinePlayer target, SpooklyPlayer source) {
        return new PunishmentImpl.Builder(target, source);
    }

    @Override
    public PlaceholderManager getPlaceholderManager() {
        return SpooklyCorePlugin.getInstance().getPlaceholderManager();
    }
}
