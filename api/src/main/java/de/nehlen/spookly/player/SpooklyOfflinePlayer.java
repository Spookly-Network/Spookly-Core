package de.nehlen.spookly.player;

import de.nehlen.spookly.database.DatabaseComponentCodec;
import de.nehlen.spookly.punishments.Punishment;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface SpooklyOfflinePlayer {

    UUID uniqueId();
    String name();
    void name(String name);

    Integer points();
    void points(Integer points);
    void addPoints(Integer points);

    /**
     * @deprecated use {@link #addPunishment(Punishment)} instead
     * @param amount
     * @param unit
     * @param reason
     * @param issuer
     */
    @Deprecated(forRemoval = true)
    void ban(Integer amount, TimeUnit unit, String reason, SpooklyPlayer issuer);

    String textureUrl();
    void textureUrl(String textureUrl);

    Instant firstPlayed();
    Instant lastPlayed();
    void lastPlayed(Instant lastPlayed);
    void addPunishment(Punishment punishment);
    void removePunishment(Punishment punishment);

    List<Punishment> getPunishments();

    /**
     * Database components are used to store data directly on the player
     * the key is here by used to identify it later, so it should be
     * unique.
     * @return a map of all database components saved on that player
     */
    @Nullable
    <T> T getDatabaseComponent(@NotNull String key, @NotNull DatabaseComponentCodec<T> codec);

    /**
     * see {@link #getDatabaseComponent(String, DatabaseComponentCodec)}
     * @param key Unique key that identifys component after loading
     * @param databaseComponent Component that should be saved in database
     */
    <T> void addDatabaseComponent(@NotNull String key, @NotNull T databaseComponent, @NotNull DatabaseComponentCodec<T> codec);
    <T> void removeDatabaseComponent(@NotNull String key);
    <T> void replaceDatabaseComponent(@NotNull String key, @NotNull T databaseComponent, @NotNull DatabaseComponentCodec<T> codec);
    Map<String, Document> getRawComponents();
    Boolean hasDatabaseComponent(@NotNull String key);
    void save();
}
