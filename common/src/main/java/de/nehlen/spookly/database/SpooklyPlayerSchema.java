package de.nehlen.spookly.database;

import de.nehlen.spookly.Spookly;
import de.nehlen.spookly.player.SpooklyOfflinePlayer;
import de.nehlen.spookly.players.SpooklyOfflinePlayerImpl;
import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class SpooklyPlayerSchema implements Schema {

    @Getter
    private String schemaName = Spookly.getServer().getSchemaPrefix() + "player";
    private Connection connection;

    public SpooklyPlayerSchema(Connection connection) {
        this.connection = connection;
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
        String query = "UPDATE " + schemaName + " SET " + field + " = ? WHERE uuid = ?";
        connection.executeUpdateAsync(query, integer -> {
        }, value, identifier);
    }

    @Override
    public void get(String field, UUID identifier, Consumer<ResultSet> consumer) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        String query = "SELECT " + field + " FROM " + schemaName + " WHERE uuid = ?";

        executorService.submit(() -> {
            try (final java.sql.Connection con = this.connection.getHikariDataSource().getConnection()) {
                try (final PreparedStatement preparedStatement = con.prepareStatement(query)) {
                    connection.addArguments(preparedStatement, identifier);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            consumer.accept(resultSet);
                            connection.close();
                        } else {
                            consumer.accept(null);
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

    public CompletableFuture<Boolean> userExists(UUID uuid) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        this.connection.executeQueryAsync("SELECT id FROM " + schemaName + " WHERE uuid = ?", resultSet -> {
            try {
                completableFuture.complete(Boolean.valueOf(resultSet.next()));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }, uuid.toString());
        return completableFuture;
    }

    public CompletableFuture<Boolean> createUser(UUID uuid, String name, String texture) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        connection.executeUpdateAsync("INSERT INTO " + schemaName + " (uuid, name, texture, points) VALUES (?, ?, ?, ?)", resultSet -> {
            if (resultSet > 0)
                completableFuture.complete(true);
            else
                completableFuture.complete(false);
        }, uuid, name, texture, 0);
        return completableFuture;
    }

    public void createUser(UUID uuid, String name, String texture, final Consumer<Boolean> consumer) {
        connection.executeUpdateAsync("INSERT INTO " + schemaName + " (uuid, name, texture, points) VALUES (?, ?, ?, ?)", resultSet -> {
            if (resultSet > 0)
                consumer.accept(true);
            else
                consumer.accept(false);
        }, uuid.toString(), name, texture, 0);
    }

    public void getOfflinePlayer(UUID uuid, final Consumer<SpooklyOfflinePlayer> callback) {
        String query = "SELECT * FROM " + schemaName + " WHERE uuid = ?";
        getOfflinePlayer(query, callback, uuid.toString());
    }

    private void getOfflinePlayer(String query, final Consumer<SpooklyOfflinePlayer> callback, String value) {
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

                            callback.accept(new SpooklyOfflinePlayerImpl(uuid, name, texture, points, lastLogin, firstLogin, this));
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
}
