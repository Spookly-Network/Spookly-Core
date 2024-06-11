package de.nehlen.spookly.database;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.SpooklyCorePlugin;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.player.SpooklyPlayer;
import de.nehlen.spookly.players.SpooklyOfflinePlayerImpl;
import de.nehlen.spookly.players.SpooklyPlayerImpl;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpooklyPlayerSchema implements Schema {

    @Getter
    private String schemaName = Spookly.getServer().getSchemaPrefix() + "player";
    private Connection connection;

    public SpooklyPlayerSchema(Connection connection) {
        this.connection = SpooklyCorePlugin.getInstance().getConnection();
    }

    @Override
    public void createSchema() {
        StringBuilder table = new StringBuilder();
        table.append("`uuid` VARCHAR(64) NOT NULL UNIQUE, ");
        table.append("`name` VARCHAR(64) NOT NULL, ");
        table.append("`texture` VARCHAR(256) NOT NULL, ");
        table.append("`points` INT(11) NOT NULL DEFAULT 0, ");
        table.append("`lastLogin` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, ");
        table.append("`firstLogin` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, ");
        table.append("PRIMARY KEY (`uuid`)");
        connection.executeUpdateAsync("CREATE TABLE IF NOT EXISTS " + schemaName + " (" + table.toString() + ")", resultSet -> {
        });
    }

    @Override
    public void set(String field, UUID identifier, Object value) {
        return;
    }

    @Override
    public void get(String field, UUID identifier, java.util.function.Consumer<ResultSet> consumer) {
        return;
    }

    public CompletableFuture<Boolean> userExists(Player player) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        this.connection.executeQueryAsync("SELECT id FROM " + schemaName + " WHERE uuid = ?", resultSet -> {
            try {
                completableFuture.complete(Boolean.valueOf(resultSet.next()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }, player.getUniqueId().toString());
        return completableFuture;
    }

    public CompletableFuture<Boolean> createUser(final Player player) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        final String playerTexture = player.getPlayerProfile().getProperties().stream()
                .filter(item -> item.getName().equals("textures"))
                .toList().get(0).getValue();
        connection.executeUpdateAsync("INSERT INTO " + schemaName + " (uuid, name, texture, points) VALUES (?, ?, ?, ?)", resultSet -> {
            if (resultSet > 0)
                completableFuture.complete(true);
            else
                completableFuture.complete(false);
        }, player.getUniqueId().toString(), player.getName(), playerTexture, 0);
        return completableFuture;
    }

    public void createUser(final Player player, final Consumer<Boolean> consumer) {
        final String playerTexture = player.getPlayerProfile().getProperties().stream()
                .filter(item -> item.getName().equals("textures"))
                .toList().get(0).getValue();

        connection.executeUpdateAsync("INSERT INTO " + schemaName + " (uuid, name, texture, points) VALUES (?, ?, ?, ?)", resultSet -> {
            if (resultSet > 0)
                consumer.accept(true);
            else
                consumer.accept(false);
        }, player.getUniqueId().toString(), player.getName(), playerTexture, 0);
    }

    public void getOfflinePlayerAsync(UUID uuid, final Consumer<SpooklyOfflinePlayer> callback) {
        String query = "SELECT * FROM " + schemaName + " WHERE uuid = ?";
        getOfflinePlayerAsync(query, callback, uuid.toString());
    }

    public SpooklyOfflinePlayer getOfflinePlayer(String name) {
        String query = "SELECT * FROM " + schemaName + " WHERE name = ?";
        return getOfflinePlayer(query, name);
    }

    public SpooklyOfflinePlayer getOfflinePlayer(UUID uuid) {
        String query = "SELECT * FROM " + schemaName + " WHERE uuid = ?";
        return getOfflinePlayer(query, uuid.toString());
    }

    private void getOfflinePlayerAsync(String query, final Consumer<SpooklyOfflinePlayer> callback, String value) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try (final java.sql.Connection con = this.connection.getHikariDataSource().getConnection()) {
                try (final PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    connection.addArguments(preparedStatement, value);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            var uuid = UUID.fromString(resultSet.getString("uuid"));
                            var name = resultSet.getString("name");
                            var texture = resultSet.getString("texture");
                            var points = resultSet.getInt("points");
                            var lastLogin = resultSet.getTimestamp("lastLogin").toInstant();
                            var firstLogin = resultSet.getTimestamp("firstLogin").toInstant();

                            callback.accept(new SpooklyOfflinePlayerImpl(uuid, name, texture, points, lastLogin, firstLogin));
                            connection.close();
                        } else {
                            callback.accept(null);
                            connection.close();
                        }
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    private SpooklyOfflinePlayer getOfflinePlayer(String query, String value) {
        try (final java.sql.Connection con = this.connection.getHikariDataSource().getConnection()) {
            try (final PreparedStatement preparedStatement = con.prepareStatement(query)) {
                connection.addArguments(preparedStatement, value);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        var uuid = UUID.fromString(resultSet.getString("uuid"));
                        var name = resultSet.getString("name");
                        var texture = resultSet.getString("texture");
                        var points = resultSet.getInt("points");
                        var lastLogin = resultSet.getTimestamp("lastLogin").toInstant();
                        var firstLogin = resultSet.getTimestamp("firstLogin").toInstant();

                        connection.close();
                        return new SpooklyOfflinePlayerImpl(uuid, name, texture, points, lastLogin, firstLogin);
                    } else {
                        connection.close();
                        return null;
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Fetch player data from database and create SpooklyPlayer instance
     *
     * @return SpooklyPlayer, null when there is no entry in database for player
     */
    //TODO make whole thing async
    public SpooklyPlayer getPlayer(@NotNull Player onlinePlayer) {
        SpooklyOfflinePlayer player = getOfflinePlayer(onlinePlayer.getUniqueId());
        if (player == null) return null;
        return SpooklyPlayerImpl.builder(onlinePlayer)
                .texture(player.textureUrl())
                .points(player.points())
                .lastPlayed(player.lastPlayed())
                .firstPlayed(player.firstPlayed())
                .build();
    }

    public void getPlayerAsync(@NotNull Player onlinePlayer, final Consumer<SpooklyPlayer> callback) {
        getOfflinePlayerAsync(onlinePlayer.getUniqueId(), player -> {
            if (player == null) {
                callback.accept(null);
                return;
            }

            callback.accept(SpooklyPlayerImpl.builder(onlinePlayer)
                    .texture(player.textureUrl())
                    .points(player.points())
                    .lastPlayed(player.lastPlayed())
                    .firstPlayed(player.firstPlayed())
                    .build());
        });
    }

    public void setPlayerPoints(@NotNull SpooklyOfflinePlayer player, @NotNull Integer points) {
        String query = "UPDATE " + schemaName + " SET points = ? WHERE uuid = ?";
        connection.executeUpdateAsync(query, integer -> {
        }, points, player.uniqueId().toString());
    }

    public void setPlayerName(@NotNull SpooklyOfflinePlayer player, @NotNull String name) {
        String query = "UPDATE " + schemaName + " SET name = ? WHERE uuid = ?";
        connection.executeUpdateAsync(query, integer -> {
        }, name, player.uniqueId().toString());
    }

    public void setPlayerTexture(@NotNull SpooklyOfflinePlayer player, @NotNull String texture) {
        String query = "UPDATE " + schemaName + " SET texture = ? WHERE uuid = ?";
        connection.executeUpdateAsync(query, integer -> {
        }, texture, player.uniqueId().toString());
    }

    public void setPlayerLastLogin(@NotNull SpooklyOfflinePlayer player, @NotNull Instant instant) {
        String query = "UPDATE " + schemaName + " SET name = ? WHERE uuid = ?";
        connection.executeUpdateAsync(query, integer -> {
        }, Timestamp.from(instant), player.uniqueId().toString());
    }
}
