package de.spookly.player;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.spookly.database.DatabaseComponentCodec;
import de.spookly.punishment.Punishment;

/**
 * Interface representing an offline player in the Spookly system.
 */
public interface SpooklyOfflinePlayer {

    /**
     * Gets the unique identifier of the player.
     *
     * @return the unique identifier of the player.
     */
    UUID uniqueId();

    /**
     * Gets the name of the player.
     *
     * @return the name of the player.
     */
    String name();

    /**
     * Sets the name of the player.
     *
     * @param name the new name of the player.
     */
    void name(String name);

    /**
     * Gets the points of the player.
     *
     * @return the points of the player.
     */
    Integer points();

    /**
     * Sets the points of the player.
     *
     * @param points the new points of the player.
     */
    void points(Integer points);

    /**
     * Adds points to the player's total.
     *
     * @param points the points to add.
     */
    void addPoints(Integer points);

    /**
     * Gets the URL of the player's texture.
     *
     * @return the texture URL of the player.
     */
    String textureUrl();

    /**
     * Sets the URL of the player's texture.
     *
     * @param textureUrl the new texture URL of the player.
     */
    void textureUrl(String textureUrl);

    /**
     * Gets the first time the player played.
     *
     * @return the first played time.
     */
    Instant firstPlayed();

    /**
     * Gets the last time the player played.
     *
     * @return the last played time.
     */
    Instant lastPlayed();

    /**
     * Sets the last time the player played.
     *
     * @param lastPlayed the new last played time.
     */
    void lastPlayed(Instant lastPlayed);

    /**
     * Adds a punishment to the player.
     *
     * @param punishment the punishment to add.
     */
    void addPunishment(Punishment punishment);

    /**
     * Removes a punishment from the player.
     *
     * @param punishment the punishment to remove.
     */
    void removePunishment(Punishment punishment);

    /**
     * Gets the list of punishments applied to the player.
     *
     * @return the list of punishments.
     */
    List<Punishment> getPunishments();

    /**
     * Gets a database component associated with the player.
     *
     * @param key   the unique key identifying the component.
     * @param codec the codec used to decode the component.
     * @param <T>   the type of the component.
     * @return the database component, or null if not found.
     */
    @Nullable
    <T> T getDatabaseComponent(@NotNull String key, @NotNull DatabaseComponentCodec<T> codec);

    /**
     * Adds a database component to the player.
     *
     * @param key                the unique key identifying the component.
     * @param databaseComponent  the component to add.
     * @param codec              the codec used to encode the component.
     * @param <T>                the type of the component.
     */
    <T> void addDatabaseComponent(@NotNull String key, @NotNull T databaseComponent, @NotNull DatabaseComponentCodec<T> codec);

    /**
     * Removes a database component from the player.
     *
     * @param key the unique key identifying the component.
     * @param <T> the type of the component.
     */
    <T> void removeDatabaseComponent(@NotNull String key);

    /**
     * Replaces a database component associated with the player.
     *
     * @param key                the unique key identifying the component.
     * @param databaseComponent  the new component to replace the old one.
     * @param codec              the codec used to encode the component.
     * @param <T>                the type of the component.
     */
    <T> void replaceDatabaseComponent(@NotNull String key, @NotNull T databaseComponent, @NotNull DatabaseComponentCodec<T> codec);

    /**
     * Gets the raw components associated with the player.
     *
     * @return a map of raw components.
     */
    Map<String, Document> getRawComponents();

    /**
     * Checks if a database component is associated with the player.
     *
     * @param key the unique key identifying the component.
     * @return true if the component exists, false otherwise.
     */
    Boolean hasDatabaseComponent(@NotNull String key);

    /**
     * Saves the player data.
     */
    void save();
}
