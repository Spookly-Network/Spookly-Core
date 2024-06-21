package de.nehlen.spookly;

import de.nehlen.spookly.configuration.ConfigurationWrapper;
import de.nehlen.spookly.database.DatabaseConnection;
import de.nehlen.spookly.events.EventExecuter;
import de.nehlen.spookly.phase.GamePhaseManager;
import de.nehlen.spookly.placeholder.PlaceholderManager;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.punishments.Punishment;
import de.nehlen.spookly.team.Team;
import de.nehlen.spookly.team.TeamManager;
import de.nehlen.spookly.team.display.TeamDisplay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface SpooklyServer {

    void getOfflinePlayer(@Nonnull String name, Consumer<SpooklyOfflinePlayer> callback);
    void getOfflinePlayer(@Nonnull UUID uuid, Consumer<SpooklyOfflinePlayer> callback);
    SpooklyPlayer getPlayer(@Nonnull Player player);
    SpooklyPlayer getPlayer(@Nonnull UUID uuid);
    List<SpooklyPlayer> getOnlinePlayers();

    EventExecuter getEventExecuter();
    String getSchemaPrefix();

    GamePhaseManager getGamePhaseManager();
    TeamManager getTeamManager();
    Team.Builder buildTeam();
    TeamDisplay createTeamDisplay(Component icon, Component prefix, TextColor color);
    ConfigurationWrapper createConfiguration(File file);
    DatabaseConnection getConnection();

    PlaceholderManager getPlaceholderManager();

    Punishment.Builder getPunishmentBuilder(SpooklyOfflinePlayer target, SpooklyPlayer source);

}
