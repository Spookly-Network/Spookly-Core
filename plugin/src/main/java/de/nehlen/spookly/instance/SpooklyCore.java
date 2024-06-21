package de.nehlen.spookly.instance;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.SpooklyServer;
import de.nehlen.spookly.configuration.Config;
import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.database.DatabaseConnection;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.placeholder.PlaceholderManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishment.PunishmentImpl;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamDisplayImpl;
import de.nehlen.spookly.team.TeamImpl;
import de.nehlen.spookly.team.TeamManager;
import de.nehlen.spookly.team.display.TeamDisplay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class SpooklyCore implements SpooklyServer {

    public SpooklyCore() {
        Spookly.setSpooklyServer(this);
    }

    @Override
    public void getOfflinePlayer(@NotNull String name, Consumer<SpooklyOfflinePlayer> callback) {
        SpooklyCorePlugin.getInstance().getPlayerManager().findOfflinePlayer(name, callback);
//        return SpooklyCorePlugin.getInstance().getPlayerSchema().getOfflinePlayer(name);
    }

    @Override
    public void getOfflinePlayer(@NotNull UUID uuid, Consumer<SpooklyOfflinePlayer> callback) {
        SpooklyCorePlugin.getInstance().getPlayerManager().findOfflinePlayer(uuid, callback);
//        return SpooklyCorePlugin.getInstance().getPlayerSchema().getOfflinePlayer(uuid);
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull Player player) {
        if(SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().containsKey(player.getUniqueId())) {
            return SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().get(player.getUniqueId());
        }
        return null;
    }

    @Override
    public SpooklyPlayer getPlayer(@NotNull UUID uuid) {
        if(SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().containsKey(uuid)) {
            return SpooklyCorePlugin.getInstance().getPlayerManager().getRegisteredOnlinePlayers().get(uuid);
        }
        return null;
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
    public TeamDisplay createTeamDisplay(Component icon, Component prefix, TextColor color) {
        return new TeamDisplayImpl(icon, prefix, color);
    }

    @Override
    public ConfigurationWrapper createConfiguration(File file) {
        return new Config(file);
    }

    @Override
    public DatabaseConnection getConnection() {
        return SpooklyCorePlugin.getInstance().getMongoDatabaseConfiguration();
//        return SpooklyCorePlugin.getInstance().getConnection();
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
