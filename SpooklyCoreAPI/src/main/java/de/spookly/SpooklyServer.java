package de.spookly;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import de.spookly.configuration.ConfigurationWrapper;
import de.spookly.database.DatabaseConnection;
import de.spookly.event.EventExecuter;
import de.spookly.phase.GamePhaseManager;
import de.spookly.placeholder.PlaceholderManager;
import de.spookly.player.SpooklyOfflinePlayer;
import de.spookly.player.SpooklyPlayer;
import de.spookly.punishment.Punishment;
import de.spookly.team.Team;
import de.spookly.team.TeamManager;
import de.spookly.team.display.TeamDisplay;

import org.bukkit.entity.Player;

/**
 * Interface representing the Spookly server, providing various services and management functionalities.
 */
public interface SpooklyServer {

    /**
     * Gets an offline player by their name.
     *
     * @param name     the name of the player.
     * @param callback the callback to handle the offline player.
     */
    void getOfflinePlayer(@Nonnull String name, Consumer<SpooklyOfflinePlayer> callback);

    /**
     * Gets an offline player by their UUID.
     *
     * @param uuid     the UUID of the player.
     * @param callback the callback to handle the offline player.
     */
    void getOfflinePlayer(@Nonnull UUID uuid, Consumer<SpooklyOfflinePlayer> callback);

    /**
     * Gets an online player by their Player object.
     *
     * @param player the Player object.
     * @return the SpooklyPlayer corresponding to the Player object.
     */
    SpooklyPlayer getPlayer(@Nonnull Player player);

    /**
     * Gets an online player by their UUID.
     *
     * @param uuid the UUID of the player.
     * @return the SpooklyPlayer corresponding to the UUID.
     */
    SpooklyPlayer getPlayer(@Nonnull UUID uuid);

    /**
     * Gets a list of all online players.
     *
     * @return the list of online players.
     */
    List<SpooklyPlayer> getOnlinePlayers();

    /**
     * Gets the event executer.
     *
     * @return the event executer.
     */
    EventExecuter getEventExecuter();

    /**
     * Gets the schema prefix.
     *
     * @return the schema prefix.
     */
    String getSchemaPrefix();

    /**
     * Gets the game phase manager.
     *
     * @return the game phase manager.
     */
    GamePhaseManager getGamePhaseManager();

    /**
     * Gets the team manager.
     *
     * @return the team manager.
     */
    TeamManager getTeamManager();

    /**
     * Creates a new team builder.
     *
     * @return the team builder.
     */
    Team.Builder buildTeam();

    /**
     * Creates a new team display with the specified properties.
     *
     * @param icon   the icon component.
     * @param prefix the prefix component.
     * @param color  the text color.
     * @return the team display.
     */
    TeamDisplay createTeamDisplay(Component icon, Component prefix, TextColor color);

    /**
     * Creates a configuration wrapper for the specified file.
     *
     * @param file the file.
     * @return the configuration wrapper.
     */
    ConfigurationWrapper createConfiguration(File file);

    /**
     * Gets the database connection.
     *
     * @return the database connection.
     */
    DatabaseConnection getConnection();

    /**
     * Gets the placeholder manager.
     *
     * @return the placeholder manager.
     */
    PlaceholderManager getPlaceholderManager();

    /**
     * Creates a punishment builder for the specified target and source players.
     *
     * @param target the target offline player.
     * @param source the source player.
     * @return the punishment builder.
     */
    Punishment.Builder getPunishmentBuilder(SpooklyOfflinePlayer target, SpooklyPlayer source);
}
